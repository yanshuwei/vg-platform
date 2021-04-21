/**
 * Simplex: Simpler than regular expressions
 */

(function() {

  /**
   * @typedef {function(string):*} Parser
   */
  var Parser;

  /**
   * @typedef {Object} SimplexOptions
   * @property {Array.<string>} fieldMarkers
   * @property {boolean} strictWhitespace
   * @property {Parser|Object.<string, Parser>} parser
   */
  var SimplexOptions;

  /**
   * @typedef {Object} MatchData
   * @property {number} index
   * @property {number} length
   */
  var MatchData;

  /**
   * A `Simplex` is sort of like a `RegExp` but simpler. The easiest way to
   * explain this is by example; see the docs for more info.
   *
   * @constructor
   * @param {string} expression
   * @param {SimplexOptions} options
   */
  function Simplex(expression, options) {
    if (!(this instanceof Simplex)) {
      return new Simplex(expression, options);
    }

    this.options = (typeof options === 'object' && options) || {};
    this.matcher = createMatcher(expression, this.options);
  }

  Simplex.prototype = {
    /**
     * Parses the given text to an object whose keys are the names of the
     * word-like tokens in the source expression for this {@link Simplex}, and
     * whose associated values are the matches for those tokens.
     *
     * A very basic attempt will be made to infer the types of the matched values;
     * i.e, values that appear to be numbers or booleans will be parsed as such.
     * All other values will simply be strings.
     *
     * @param {string} text
     * @returns {Object}
     *
     * @example
     * Simplex('a=b(c) d/e').match('foo=bar(baz) yes/no');
     * // => {
     *   a: 'foo',
     *   b: 'bar',
     *   c: 'baz',
     *   d: 'yes',
     *   e: 'no'
     * }
     *
     * Simplex('(x, y)').match('(1, 3)');
     * // => { x: 1, y: 3 }
     *
     * Simplex('<tags*>').match('blah <foo bar> blah');
     * // => { tags: 'foo bar' }
     *
     * Simplex('[exclamation*]!', { fieldMarkers: '[]' })
     *   .match('Hello there, Dan!');
     * // => { exclamation: 'Hello there, Dan'}
     *
     * // Be lenient w/ whitespace by default.
     * Simplex('a b c').match('foo   bar\n\tbaz');
     * // => { a: 'foo', b: 'bar', c: 'baz' }
     *
     * // Fields can be multiple words when field markers are specified
     * Simplex('([foo bar])', { fieldMarkers: '[]' }).match('(baz)');
     * // => { 'foo bar': 'baz' }
     */
    match: function match(text) {
      var regexMatch = text.match(this.matcher.pattern);
      if (!regexMatch) {
        return null;
      }
      return mapMatch(regexMatch, this.matcher.map, this.options.parser);
    },

    /**
     * Behaves like {@link #match}, but returns an array with *all* matches from
     * the given string.
     *
     * @param {string} text
     * @returns {Object}
     *
     * @example
     * Simplex('pairName=[x,y]').matchAll('foo=[a,b]&bar=[c,d]');
     * // => [
     *   {
     *     pairName: 'foo',
     *     x: 'a',
     *     y: 'b'
     *   },
     *   {
     *     pairName: 'bar',
     *     x: 'c',
     *     y: 'd'
     *   }
     * ]
     */
    matchAll: function matchAll(text) {
      var pattern = new RegExp(this.matcher.pattern.source, 'g'),
          map     = this.matcher.map,
          regexMatch;

      var results = [];
      while (regexMatch = pattern.exec(text)) {
        results.push(mapMatch(regexMatch, map, this.options.parser));
      }

      return results;
    }
  };

  /**
   * @private
   * @param {MatchData} match
   * @param {Array.<string>} map
   * @param {Parser|Object.<string, Parser>} parser
   *
   * @example
   * var fooBarMatch = 'foo=bar'.match(/(\w+)=(\w+)/),
   *     numberMatch = '11'.match(/(\d+)/),
   *     customMatch = 'oct 31 == dec 25?'.match(/oct (\d+) == dec (\d+)\?/);
   *
   * mapMatch(fooBarMatch, ['name', 'value']);
   * // => { name: 'foo', value: 'bar' }
   *
   * // Test custom parser
   * mapMatch(numberMatch, ['x'], function(s) { return parseInt(s, 16); });
   * // => { x: 17 }
   *
   * // Test parser map
   * mapMatch(customMatch, ['oct', 'dec'], {
   *   oct: function(s) { return parseInt(s, 8); },
   *   dec: function(s) { return parseInt(s, 10); }
   * });
   * // => { oct: 25, dec: 25 }
   */
  function mapMatch(match, map, parser) {
    parser = wrapParser(parser);

    var data = {};

    for (var i = 0, len = map.length; i < len; ++i) {
      if (i > match.length) {
        break;
      }

      data[map[i]] = parser(match[i + 1], map[i]);
    }
    return data;
  }

  /**
   * @private
   * @param {Parser|Object.<string, Parser>} parser
   * @returns {function(string):Parser}
   */
  function wrapParser(parser) {
    if (typeof parser === 'function') {
      return parser;
    }

    if (typeof parser !== 'object' || !parser) {
      return weakParse;
    }

    return function(match, fieldName) {
      var parse = parser[fieldName];

      if (typeof parse === 'function') {
        return parse(match);
      }

      return match;
    };
  }

  /**
   * @private
   * @param {string} string
   * @returns {number|boolean|string}
   *
   * weakParse('123');  // => 123
   * weakParse('true'); // => true
   * weakParse('foo');  // => 'foo'
   * weakParse('123a'); // => '123a'
   * weakParse('tru');  // => 'tru'
   */
  function weakParse(string) {
    if (/^\d+$/.test(string)) {
      return Number(string);
    } else if (/^(?:true|false)$/.test(string)) {
      return Boolean(string);
    }

    return string;
  }

  /**
   * @private
   * @param {string} expression
   * @param {SimplexOptions} options
   *
   * @example
   * createMatcher('name=value', {});
   * // => {
   *   pattern: /(\w+)=(\w+)/,
   *   map: ['name', 'value']
   * }
   *
   * createMatcher('{ name: value }', { strictWhitespace: true });
   * // => {
   *   pattern: /\{ (\w+): (\w+) \}/,
   *   map: ['name', 'value']
   * }
   *
   * createMatcher('[name] foo [value]', { fieldMarkers: '[]' });
   * // => {
   *   pattern: /(\w+)\s+foo\s+(\w+)/,
   *   map: ['name', 'value']
   * }
   */
  function createMatcher(expression, options) {
    var fieldMarkers = parseFieldMarkers(options.fieldMarkers),
        fieldMatcher = getFieldMatcher(fieldMarkers),
        fieldMatch,
        pattern = '',
        index = 0,
        map = [];

    while (fieldMatch = fieldMatcher.exec(expression)) {
      pattern += getPatternSegment(expression.substring(index, fieldMatch.index), options);

      if (isMultiwordField(fieldMatch[0], fieldMarkers)) {
        pattern += '(.*)';
      } else {
        pattern += '(\\w+)';
      }

      index = fieldMatch.index + fieldMatch[0].length;

      map.push(fieldMatch[1]);
    }

    if (index < expression.length) {
      pattern += getPatternSegment(expression.substring(index), options);
    }

    return {
      pattern: new RegExp(pattern),
      map: map
    };
  }

  /**
   * @private
   * @param {Array.<string>|Object|string} input
   * @return {Array.<string>|null}
   *
   * @example
   * parseFieldMarkers(null);       // => null
   * parseFieldMarkers('[]');       // => ['[', ']']
   * parseFieldMarkers('{{}}');     // => ['{{', '}}']
   * parseFieldMarkers(['a', 'b']); // => ['a', 'b']
   * parseFieldMarkers('<*>');      // => ['<*', '*>']
   */
  function parseFieldMarkers(input) {
    if (!input) {
      return null;
    }

    if (typeof input === 'string') {
      return [
        input.substring(0, Math.ceil(input.length / 2)),
        input.substring(Math.floor(input.length / 2))
      ];
    }

    return input;
  }

  /**
   * @private
   * @param {Array.<string>?} fieldMarkers
   * @returns {RegExp}
   *
   * @example
   * getFieldMatcher(null);       // => /(\w+)\*?/g
   * getFieldMatcher(['{', '}']); // => /\{([\w\s]+)\*?\}/g
   */
  function getFieldMatcher(fieldMarkers) {
    if (!fieldMarkers) {
      return /(\w+)\*?/g;
    }

    var left  = escapeRegex(fieldMarkers[0]),
        right = escapeRegex(fieldMarkers[1]);

    // If we're using field markers, then we can allow spaces in field names.
    return new RegExp(left + '([\\w\\s]+)\\*?' + right, 'g');
  }

  /**
   * @private
   * @param {string} segment
   * @returns {string}
   *
   * @example
   * getPatternSegment('foo bar', {}); // => 'foo\\s+bar'
   */
  function getPatternSegment(segment, options) {
    segment = escapeRegex(segment)

    if (!options.strictWhitespace) {
      segment = segment.replace(/\s+/g, '\\s+');
    }

    return segment;
  }

  /**
   * @private
   * @param {string} match
   * @param {Array.<string>?} fieldMarkers
   * @return {boolean}
   */
  function isMultiwordField(match, fieldMarkers) {
    if (fieldMarkers && fieldMarkers[1]) {
      return new RegExp('\\*' + escapeRegex(fieldMarkers[1])).test(match);
    }

    return /\*$/.test(match);
  }

  /**
   * @private
   * @param {string} source
   *
   * @example
   * escapeRegex('^hi$'); // => '\\^hi\\$'
   */
  function escapeRegex(source) {
    return (source || '').replace(/([\(\)\[\]\{\}\^\$])/g, '\\$1');
  }

  // Expose in Node.js
  if (typeof module === 'object' && module && module.exports) {
    module.exports = Simplex;

  // Or in a browser window/web worker
  } else {
    this.Simplex = Simplex;
  }

}).call(this);
