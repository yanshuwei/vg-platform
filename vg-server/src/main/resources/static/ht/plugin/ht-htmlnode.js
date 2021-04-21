!function(m){"use strict";var X="ht",i=m[X],A=function(){return document},o=function(){return A().body},C=function(e,O,Z){e.style.setProperty(O,Z,null)},x=function(n){return A().createElement(n)},T=function(){return x("div")},Z=function(){var N=x("canvas");return N},y=function(o,K){C(o,"-webkit-transform",K),C(o,"-ms-transform",K),C(o,"transform",K)},w=function(M,j){C(M,"-webkit-transform-origin",j),C(M,"-ms-transform-origin",j),C(M,"transform-origin",j)},M=function(f,i){f.appendChild(i)},D=function(B,s){B.removeChild(s)},S=m.parseInt,K=i.Default,f=K.eventListenerOptionsFalse,I=(K.eventListenerOptionsTrue,function(m,G,v,W){m.addEventListener(G,v,W?f:f)}),G=K.getInternal(),v=Math.PI,U="white-space",$="visibility",h="left",b="top",r="width",n="height",Q="position",F="display",d="z-index",p="px",s="0 0",_="absolute",R="visible",H="hidden",a="none",q="block",B="nowrap",g="rgba(0, 0, 0, 0.005)";K.setImage("node_dragger","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAN9JREFUeNrsV9sNhDAMKyzQVdgARmGzrMJNUFZhAh6nfkVcG9PQgHSR8lEksJs6pnGuLCimSRzAa0yyBK9O4gy8GokU+O0kJOAwiQYg0LP1xNYDW3+0CfBYNb7VOuN4LAGpiOaYUhFDas9F2NPHDELNENJqaHgBgSQJ3ufakfQJqckERcOiK+Ae1FGWBNKGh9oX5WPpLpdNYfffijbsxTHh7VKP7388n1g1h7OKUoUuyGpJakQEuhwkZAKcDXVOdWcrOrL/feBVBHI/q8fcjE1nA9PpyHQ+NJ2Qi8A3AQYAOtS27fCoRY0AAAAASUVORK5CYII=");var Y=i.graph.GraphView.prototype,N=Y._42;Y.adjustHtmlNodeIndex=!0,Y._42=function(I,q){if(N.call(this,I,q),this.adjustHtmlNodeIndex)for(var b=this.getDataModel()._datas._as,A=b.length,h=1,u=0;A>u;u++){var Z=b[u];if(Z instanceof t){var w=this.getDataUI(Z);C(w.$2f,d,h+""),C(w.$3f,d,h+1+""),h+=2}}};var J=i.HtmlNodeUI=function(S,b){var r=this;J.superClass.constructor.call(r,S,b);var h=r.$2f=T(),i=r.$3f=Z();C(h,Q,_),C(h,$,H),C(h,U,B),i.draggable=!1,C(i,Q,_),C(i,F,a),w(i,s),I(h,"change",function(x){var m=x.target,$=m.bind||m.getAttribute("bind"),w=m.type&&"checkbox"===m.type?m.checked:m.value,D=b.getContext();$&&D&&(D[$]=w,r.$4f=JSON.stringify(D))}),["mousedown","touchstart","keydown","mousewheel","DOMMouseScroll"].forEach(function(F){I(h,F,this.$9f.bind(this))},r)};K.def(J,G.ui().NodeUI,{_visible:!0,$11f:function(){var m=this,Q=m.$3f,b=m._data,P=b.getDraggerImageWidth(),n=b.getDraggerImageHeight(),U=b.getDraggerImage(),g=G.initContext(Q);g.beginPath(),G.setCanvas(Q,P,n),G.translateAndScale(g,0,0,1),g.clearRect(0,0,P,n),K.drawImage(g,K.getImage(U),0,0,P,n),g.restore()},_80o:function(i){J.superClass._80o.call(this,i);var Y=this,z=Y._data,O=z._padding,j=2*O,e=Y.$2f,W=Y.$3f,s=Y.gv,K=s.getZoom(),I=s.getTranslateX(),N=s.getTranslateY(),o=s.getView(),B=Y._83o,E=z._width,u=z._height,t=B.position,G=B.rotation,k=(E-j)/z.$5f*K,U=(u-j)/z.$6f*K,V=Y._html,w=z._html,x=z.getHtmlType();if("html"===x){var X=z.getContext()||{},m=Y.$4f,l=z.$10f,Z=JSON.stringify(X);V&&m&&V===w&&m===Z||(Y.$4f=Z,Y._html=w,e.innerHTML=l?l(X):w)}else if(null!=x){var P=z.getHtml();"ht"===x&&(P=P.getView()),V&&V===P&&e.contains(V)||(Y._html=P,e.innerHTML="",M(e,P))}if(!e.parentNode){var D=s.$1f;if(!D){var f=T();C(f,Q,_),C(f,d,"0"),D=s.$1f=f;var L=s._canvas.nextSibling;L?o.insertBefore(f,L):M(o,f)}M(D,e),M(D,W),z.onContentInitialized&&z.onContentInitialized(e)}if(z._scalable){var c=z.$5f,A=z.$6f;y(e,"rotate("+180*(G/v)+"deg) scale("+k+","+U+")"),C(e,r,""),C(e,n,""),C(e,h,(t.x-c/K/2)*K+I+p),C(e,b,(t.y-A/K/2)*K+N+p)}else{var gq=S(e.style.width),re=S(e.style.height),Sk=S((E-j)*K),ze=S((u-j)*K),Cj="100%",Ob=e.children[0];C(Ob,r,Cj),C(Ob,n,Cj),(gq!==Sk||re!==ze)&&(C(e,r,Sk+p),C(e,n,ze+p),"ht"===x&&w.invalidate()),y(e,"rotate("+180*(G/v)+"deg)"),C(e,h,(t.x-Sk/K/2)*K+I+p),C(e,b,(t.y-ze/K/2)*K+N+p)}var Kg=Y.dragRect;s.isMovable(z)&&s.isSelected(z)&&Kg?(i.save(),i.fillStyle=g,i.fillRect(Kg.x,Kg.y,Kg.width,Kg.height),i.restore(),C(W,h,Kg.x*K+I+p),C(W,b,Kg.y*K+N+p),y(W,"scale("+K+","+K+")"),C(W,F,q),Y.$11f()):C(W,F,a),C(e,$,this._visible?R:H)},dispose:function(){var F=this.gv.$1f;this.$2f.parentNode===F&&F.removeChild(this.$2f),this.$3f.parentNode===F&&F.removeChild(this.$3f)},_84o:function(b){this._visible=b,C(this.$2f,$,b?R:H),C(this.$3f,F,b?q:a)},_3O:function(){var H=this,o=H.gv,s=H._data;J.superClass._3O.call(H);var e=s.getRect();o.isEditable(s)&&(H.dragRect={x:e.x+e.width+s._padding,y:e.y+10,width:s.getDraggerImageWidth(),height:s.getDraggerImageHeight()},H._68o(H.dragRect))},rectIntersects:function(B){var S=this._79o();return i.Default.intersection(S,B)?!0:void 0},$9f:function(N){var x=this.gv,Z=this._data;x.sm().contains(Z)&&N.stopPropagation()}});var t=i.HtmlNode=function(){t.superClass.constructor.call(this)};i.Default.def(t,i.Node,{ms_ac:["html","context","scalable","padding","draggerImage","draggerImageWidth","draggerImageHeight"],_padding:i.Default.isTouchable?12:6,_image:null,_scalable:!0,_draggerImage:"node_dragger",_draggerImageWidth:20,_draggerImageHeight:20,setHtml:function(z){var w=this,Q=w._html;w._html=z,"html"===w.getHtmlType()&&"Handlebars"in m&&(w.$10f=Handlebars.compile(z)),w.$13f(),w.fp("html",Q,z)},setContext:function(h){var u=this,F=u._context;u._context=h,u.fp("context",F,h),u.$13f()},setScalable:function(y){var N=this,H=N._scalable;N._scalable=y,N.fp("scalable",H,y),N.$13f()},getHtmlType:function(){var v=this._html;return v?"string"==typeof v?"html":v.getView?"ht":"dom":null},$13f:function(){var m=this,y=m._html,f=m.$10f;if(y){var N=T(),X=!1,i=m.getHtmlType();if(C(N,Q,_),C(N,U,B),C(N,$,H),"html"===i?(N.innerHTML=f?f(m.getContext()||{}):y,X=!0):"ht"===i?(M(N,y.getView()),X=!0):"dom"===i&&(M(N,y),X=!0),X){var V=2*m._padding;M(o(),N),m.$5f=N.scrollWidth,m.$6f=N.scrollHeight,m._width=m.$5f+V,m._height=m.$6f+V,m._originWidth=m._width,m._originHeight=m._height,D(o(),N)}}},getUIClass:function(){return i.HtmlNodeUI}})}("undefined"!=typeof global?global:"undefined"!=typeof self?self:"undefined"!=typeof window?window:(0,eval)("this"),Object);