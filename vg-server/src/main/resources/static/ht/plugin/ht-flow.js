!function(E){"use strict";var Y="ht",n=E[Y],I=n.Default,H=Math,k=H.PI,M=2*k,T=H.sin,K=H.cos,G=H.atan2,z=H.sqrt,g=H.max,X=H.floor,Z=(H.round,H.ceil),s=H.abs,u=n.Shape,b=n.Edge,l=n.List,i=n.Style,x=n.graph,c=I.getInternal(),_=c.ui(),A=null,O="__segmentLengths",y="__lineTotalLength",L="__linePoints",R="__distance0",j="flow.count",d="flow.step",w="flow.element.max",Q="flow.element.count",m="flow.element.min",f="flow.element.space",$="flow.element.autorotate",r="flow.element.background",e="flow.element.shadow.max",v="flow.element.shadow.min",J="flow.element.shadow.begincolor",p="flow.element.shadow.endcolor",P="flow.element.shadow.visible",V="flow.element.image",q="flow",D="prototype",t=x.GraphView[D],N=n.Data[D],B=_.DataUI[D],F=_.ShapeUI[D],C=_.EdgeUI[D],U=n.DataModel[D],o=F._80o,h=C._80o,a=U.prepareRemove,S=C._79o,W=F._79o,qr=t.setDataModel,_k=function(S){return document.createElement(S)};t.calculatePointLength=function(m,l,O){var J=this,X=J.getDataUI(m);O==A&&(O=.1);var n=ij(X),f=[];if(n){for(var x=n.length,Y=0;x>Y;Y++){var V=n[Y];V._as&&(V=V._as);for(var h=V[0],H=1;H<V.length;H++)f.push([h,V[H]]),h=V[H]}for(var W=[],Y=0;Y<f.length;Y++){var o=rl(f[Y][0],f[Y][1],l);W.push(o)}var B=W.slice(0);B.sort(function(E,v){return E.z>v.z?1:E.z<v.z?-1:0});var s=B[0];if(s.z<O){for(var v=W.indexOf(s),t=0,Y=0;v>=Y;Y++)t+=v>Y?I.getDistance(f[Y][0],f[Y][1]):I.getDistance(f[Y][0],s);return t}}},I.calculatePointLength=function(u,y,d,i){i==A&&(i=.1);var T=ij(A,u,y),y=[];if(T){for(var k=T.length,e=0;k>e;e++){var x=T[e];x._as&&(x=x._as);for(var E=x[0],K=1;K<x.length;K++)y.push([E,x[K]]),E=x[K]}for(var P=[],e=0;e<y.length;e++){var n=rl(y[e][0],y[e][1],d);P.push(n)}var N=P.slice(0);N.sort(function(L,B){return L.z>B.z?1:L.z<B.z?-1:0});var h=N[0];if(h.z<i){for(var a=P.indexOf(h),g=0,e=0;a>=e;e++)g+=a>e?I.getDistance(y[e][0],y[e][1]):I.getDistance(y[e][0],h);return g}}},t.calculateClosestPoint=function(f,O){var k=this,E=k.getDataUI(f),D=ij(E),i=[];if(D){for(var Y=D.length,g=0;Y>g;g++){var v=D[g];v._as&&(v=v._as);for(var l=v[0],A=1;A<v.length;A++)i.push([l,v[A]]),l=v[A]}for(var m=[],g=0;g<i.length;g++){var o=rl(i[g][0],i[g][1],O);m.push(o)}return m.sort(function(i,M){return i.z>M.z?1:i.z<M.z?-1:0}),m[0]}},I.calculateClosestPoint=function(Y,J,E){var k=ij(A,Y,J),J=[];if(k){for(var N=k.length,D=0;N>D;D++){var $=k[D];$._as&&($=$._as);for(var d=$[0],I=1;I<$.length;I++)J.push([d,$[I]]),d=$[I]}for(var e=[],D=0;D<J.length;D++){var F=rl(J[D][0],J[D][1],E);e.push(F)}return e.sort(function(B,t){return B.z>t.z?1:B.z<t.z?-1:0}),e[0]}},t.getPercentAngle=function(o,n){var L=this,e=L.getDataUI(o),w=o.getRotation?o.getRotation():0,k=ij(e);if(k)if(0===n){var x=k[0][0],U=k[0][1];w+=G(U.y-x.y,U.x-x.x)}else if(100===n){k=k[k.length-1];var x=k[k.length-2],U=k[k.length-1];w+=G(U.y-x.y,U.x-x.x)}else{for(var C=0,l=[],u=k.length,$=0;u>$;$++){var X=k[$],m=Wn(X);C+=m,l.push(m)}for(var S=C*n/100,N=ts(S,l),_=0,B=0;N>B;B++)_+=l[B];S-=_;for(var H=Vp(k[N],S),Q=k[N],O=0,d=0,s=0;s<Q.length-1;s++){var T=Q[s],g=Q[s+1],D=g.x-T.x,Y=g.y-T.y,b=z(D*D+Y*Y);if(O+=b,O>S){d=s;break}}var A=Q[d];w+=G(H.y-A.y,H.x-A.x)}return w},I.getPercentAngle=function(C,P,p){var r=0,j=ij(A,C,P);if(j)if(0===p){var d=j[0][0],F=j[0][1];r+=G(F.y-d.y,F.x-d.x)}else if(100===p){j=j[j.length-1];var d=j[j.length-2],F=j[j.length-1];r+=G(F.y-d.y,F.x-d.x)}else{for(var h=0,M=[],H=j.length,c=0;H>c;c++){var T=j[c],u=Wn(T);h+=u,M.push(u)}for(var $=h*p/100,b=ts($,M),E=0,S=0;b>S;S++)E+=M[S];$-=E;for(var n=Vp(j[b],$),I=j[b],Y=0,e=0,t=0;t<I.length-1;t++){var Z=I[t],X=I[t+1],x=X.x-Z.x,o=X.y-Z.y,Q=z(x*x+o*o);if(Y+=Q,Y>$){e=t;break}}var q=I[e];r+=G(n.y-q.y,n.x-q.x)}return r},t.calculateLength=function(W){var R=this,L=R.getDataUI(W),U=ij(L),u=0;if(U)if(Array.isArray(U[0]))for(var g=U.length,h=0;g>h;h++){var m=U[h],E=Wn(m);u+=E}else u=Wn(U);return u},I.calculateLength=function(x,B){var x=ij(A,x,B),J=0;if(x)if(Array.isArray(x[0]))for(var Z=x.length,D=0;Z>D;D++){var c=x[D],T=Wn(c);J+=T}else J=Wn(x);return J};var rl=I.calculateClosestPointOnLine=function(T,M,i){var Q=T.x,Y=T.y,j=M.x,N=M.y,k=i.x,w=i.y,E={},_=j-Q,K=N-Y,x=Math.sqrt(_*_+K*K),h=_/x,I=K/x,g=(-Q+k)*h+(-Y+w)*I;return E.x=Q+g*h,E.y=Y+g*I,gs(E,T,M)||(E.x=Math.abs(E.x-T.x)<Math.abs(E.x-M.x)?T.x:M.x,E.y=Math.abs(E.y-T.y)<Math.abs(E.y-M.y)?T.y:M.y),_=k-E.x,K=w-E.y,E.z=Math.sqrt(_*_+K*K),E},gs=function(e,t,w){return e.x>=Math.min(t.x,w.x)&&e.x<=Math.max(t.x,w.x)&&e.y>=Math.min(t.y,w.y)&&e.y<=Math.max(t.y,w.y)},Wn=function(o){for(var T=0,Z=o.length-1,f=0;Z>f;f++){var I=o[f],l=o[f+1],M=l.x-I.x,c=l.y-I.y,F=z(M*M+c*c);T+=F}return T},Vp=function(I,J){for(var i=0,l=0,j=0,y=I.length-1,n=0;y>n;n++){var U=I[n],f=I[n+1],p=f.x-U.x,$=f.y-U.y;if(j=z(p*p+$*$),i+=j,i>J){i-=j,l=n;break}}var L=I[l],F=I[l+1],m=G(F.y-L.y,F.x-L.x),b=J-i,$=T(m)*b,p=K(m)*b;return{x:L.x+p,y:L.y+$}},Sq=function(y,k,j,Z){var Q=K(Z),b=T(Z),t=Q*k-b*j,q=b*k+Q*j;return y?{x:y.x+t,y:y.y+q}:{x:t,y:q}},er=function(P,o){P[O]=P[y]=P[L]=o[R]=A},sk=function(X,u,U,P,f,b){var Q,S,i,r,m,d,A,J,N,y,n,D=[];if(s(P)>M&&(P=M),m=Z(s(P)/(k/4)),Q=P/m,S=-Q,i=-U,m>0){d=X+K(U)*f,A=u+T(-U)*b,D.push({x:d,y:A});for(var x=0;m>x;x++)i+=S,r=i-S/2,J=X+K(i)*f,N=u+T(i)*b,y=X+K(r)*(f/K(S/2)),n=u+T(r)*(b/K(S/2)),D.push({x:y,y:n}),D.push({x:J,y:N})}return D},ij=function(U,g,p){if(g==A){var J=U._data;if(J instanceof u){if(g=J.getPoints(),p=J.getSegments(),(!p||0===p.size())&&g){p=new n.List([1]);for(var s=1;s<g.size();s++)p.add(2)}}else if(J instanceof b){var m=U._78o;if(m){var K=m.type,N=m.points,V=m.segments,j=m.edgeTypeInfo;if(!K||N){var v=m.sourcePoint,a=v.x,X=v.y,I=m.targetPoint,o=I.x,Z=I.y;if(K)V?(g=new l({x:a,y:X}),g.addAll(N),g.add({x:o,y:Z}),p=new l(V._as)):(g=new l({x:a,y:X}),p=new l([1]),N.each(function(T){g.add(T),p.add(2)}),g.add({x:o,y:Z}),p.add(2));else if(m.looped){g=new l(sk(a,X,0,M,m.radius,m.radius)),p=new l([1]);for(var s=0;s<(g.size()-1)/2;s++)p.add(3)}else g=new l,m.center?(g.add({x:m.c1.x,y:m.c1.y}),g.add({x:a,y:X}),g.add({x:o,y:Z}),g.add({x:m.c2.x,y:m.c2.y}),p=new l([1,2,2,2])):(g.add({x:a,y:X}),g.add({x:o,y:Z}),p=new l([1,2]))}else if(j)if(g=new l(j.points._as),j.segments)p=new l(j.segments._as);else{p=new l([1]);for(var s=1;s<j.points.size();s++)p.add(2)}}}}if(g){if(Array.isArray(g)&&(g=new l(g)),"number"==typeof g.get(0)){for(var w=new n.List,s=0;s<g.size();s+=2)w.add({x:g.get(s),y:g.get(s+1)});g=w}if(!p){p=[];for(var s=0;s<g.size();s++)0===s?p.push(1):p.push(2)}Array.isArray(p)&&(p=new l(p));for(var G=c.toPointsArray(g._as,p._as,50),q=G.length,S=[],s=0;q>s;s++){var F=G[s];F.length>1&&S.push(F)}return S}},Gc=function(U){var d=U._data,t=ij(U);if(t){d.s("flow.reverse")&&(t.reverse(),t.forEach(function(s){s.reverse()}));for(var X=0,u=[],m=t.length,E=0;m>E;E++){var j=t[E],Z=Wn(j);X+=Z,u.push(Z)}if(d[O]=u,d[y]=X,d[L]=t,d instanceof b){var o=I.unionPoint(t),D=o.x+o.width/2,f=o.y+o.height/2;d.$10e={x:D,y:f}}Pq(U,!0)}},Pq=(I.getPercentPositionOnPoints=function(R,K,T){if(R){var y=ij(A,R,K);if(y){var $;if(0===T)$=y[0][0];else if(100===T)y=y[y.length-1],$=y[y.length-1];else{for(var E=0,X=[],F=y.length,k=0;F>k;k++){var d=y[k],n=Wn(d);E+=n,X.push(n)}for(var e=E*T/100,q=ts(e,X),m=0,O=0;q>O;O++)m+=X[O];e-=m,$=Vp(y[q],e)}return $}}},t.getPercentPosition=function(U,x){var I=this,N=I.getDataUI(U),D=ij(N);if(D){var F;if(0===x)F=D[0][0];else if(100===x)D=D[D.length-1],F=D[D.length-1];else{for(var s=0,V=[],d=D.length,f=0;d>f;f++){var h=D[f],n=Wn(h);s+=n,V.push(n)}for(var P=s*x/100,C=ts(P,V),$=0,t=0;C>t;t++)$+=V[t];P-=$,F=Vp(D[C],P)}return F}},function(c,k){var N=c._data,Y=N[y],V=N.s(j),l=N.s(d),r=0,p=N[O],E=N.s(w),g=N.s(m),A=N.s(Q),v=(E-g)/(A-1),U=[];if(p){if(1===A)U.push(E);else if(2===A)U.push(E),U.push(g);else{if(!(A>2))return;U.push(E);for(var h=A-2;h>0;h--)U.push(g+v*h);U.push(g)}var z=0,s=0;U.forEach(function(Y){A-1>z&&(s+=N.getFlowElementSpace(Y)),z++}),s+=(E+g)/2,r=(Y-V*s+s)/V;var q=c[R];for(null==q&&(q=0),k||(q+=l);q>Y+s;){var B=c._overCount;B?B++:B=1,B>=V&&(B=null),c._overCount=B,N.s("flow.autoreverse")?B?q-=r+s:(q=0,N.s("flow.reverse",!N.s("flow.reverse"))):q-=r+s}c[R]=q}}),Ub=function(Z){var E=Z.data,C=this.dm();if(E&&"add"===Z.kind){var u=C.$3e;u&&E.s(q)&&u.indexOf(E)<0&&u.push(E)}"clear"===Z.kind&&(C.$3e=[])},Nm=function(m){var e=m.property,p=m.data,T=m.newValue,E=this.dm().$3e;if(E&&"s:flow"===e)if(T)E.indexOf(p)<0&&E.push(p);else for(var C=E.length,Z=0;C>Z;Z++)if(E[Z]===p){E.splice(Z,1);break}},ts=ts=function(v,d){for(var c=0,e=d.length,O=0;e>O;O++){var t=d[O];if(c+=t,c>v)return O}return Math.min(O,e-1)},of=function(Z){var t=this,d=t._data,I=d[y],H=d[O],K=d[L],E=d.s(j),W=0,q=t[R],V=d.s(w),f=d.s(m),h=d.s(Q),C=d.s(v),X=d.s(e),D=d.s($),o=(X-C)/(h-1),b=(V-f)/(h-1),T=d.getRotation?d.getRotation():0,Y=d.getPosition?d.p():d.$10e,n=[],s=[];if(q!=A){if(1===h)n.push(V);else if(2===h)n.push(V),n.push(f);else{if(!(h>2))return;n.push(V);for(var l=h-2;l>0;l--)n.push(f+b*l);n.push(f)}if(1===h)s.push(X);else if(2===h)s.push(X),s.push(C);else{if(!(h>2))return;s.push(X);for(var l=h-2;l>0;l--)s.push(C+o*l);s.push(C)}var c=0,F=0;n.forEach(function(x){h-1>c&&(F+=d.getFlowElementSpace(x)),c++}),F+=(V+f)/2,W=(I-E*F+F)/E,Z.save();for(var l=0;E>l;l++){var J=q,k=0,r=t._overCount,u=0;d.s("flow.autoreverse")&&r&&r>E-(l+1)||(J-=l*(W+F),c=0,n.forEach(function(B){var _=J-k;if(_>=0&&I>_){var p=!0,Q=ts(_,H);u=0;for(var A=0;Q>A;A++)u+=H[A];if(_-=u,p){var V=Vp(K[Q],_),r=T;if(D){for(var P=K[Q],i=0,g=0,C=0;C<P.length-1;C++){var m=P[C],v=P[C+1],l=v.x-m.x,W=v.y-m.y,q=z(l*l+W*W);if(i+=q,i>_){g=C;break}}var F=P[g];r+=G(V.y-F.y,V.x-F.x)}T&&(V=Sq(Y,V.x-Y.x,V.y-Y.y,T)),t.$5e(Z,V,B,s[c],r)}}k+=d.getFlowElementSpace(n[c]),c++}))}Z.restore()}},_q=function(){var D=this,x=D._data,g=x.s(w),p=!1,N=A;if(D._6I||(p=!0),N=x instanceof b?S.call(D):W.call(D),x.s(q)&&p){var Q=x.s(e),r=x.s(P);r&&Q>g&&(g=Q),g>0&&I.grow(N,Z(g/2)),Gc(D)}return!x.s(q)&&p&&er(x,D),N};N.getFlowElementSpace=function(){return this.s(f)},F._79o=_q,C._79o=_q,i[w]==A&&(i[w]=7),i[m]==A&&(i[m]=0),i[j]==A&&(i[j]=1),i[d]==A&&(i[d]=3),i[Q]==A&&(i[Q]=10),i[f]==A&&(i[f]=3.5),i[$]==A&&(i[$]=!1),i[r]==A&&(i[r]="rgba(255, 255, 114, 0.4)"),i[J]==A&&(i[J]="rgba(255, 255, 0, 0.3)"),i[p]==A&&(i[p]="rgba(255, 255, 0, 0)"),i[P]==A&&(i[P]=1),i[e]==A&&(i[e]=22),i[v]==A&&(i[v]=4),U.prepareRemove=function(f){a.call(this,f);var g=f._dataModel,N=g.$3e;if(N)for(var s=N.length,L=0;s>L;L++)if(N[L]===f){N.splice(L,1);break}},t.setDataModel=function(A){var a=this,z=a._dataModel;if(z!==A){z&&(z.umm(Ub,a),z.umd(Nm,a),z.$3e=[]),A.mm(Ub,a),A.md(Nm,a);var k=A.$3e=[];A.each(function(x){x.s(q)&&k.indexOf(x)<0&&k.push(x)}),qr.call(a,A)}},t.setFlowInterval=function(s){var H=this,w=H.$11e;H.$11e=s,H.fp("flowInterval",w,s),H.$7e!=A&&(clearInterval(H.$7e),delete H.$7e,H.enableFlow(s))},t.getFlowInterval=function(){return this.$11e},t.$9e=function(){var V,Y,q,T=this,y=T.tx(),t=T.ty(),u=T.getZoom(),K=T.getWidth(),x=T.getHeight(),C={x:-y/u,y:-t/u,width:K/u,height:x/u},B=T.dm().$3e,H=T._56I,M=new l;if(B.forEach(function(e){H[e.getId()]&&(V=T.getDataUI(e),V&&(q=V._79o(),q&&M.add(q)))}),0!==M.size()&&(M.each(function(h){I.intersectsRect(C,h)&&(Y=I.unionRect(Y,h))}),Y&&(Y&&(I.grow(Y,g(1,1/u)),Y.x=X(Y.x*u)/u,Y.y=X(Y.y*u)/u,Y.width=Z(Y.width*u)/u,Y.height=Z(Y.height*u)/u,Y=I.intersection(C,Y)),Y))){var z=T._canvas.getContext("2d");z.save(),z.lineCap=I.lineCap,z.lineJoin=I.lineJoin,c.translateAndScale(z,y,t,u),z.beginPath(),z.rect(Y.x,Y.y,Y.width,Y.height),z.clip(),z.clearRect(Y.x,Y.y,Y.width,Y.height),T.$6e(z,Y),z.restore()}},t.$6e=function(P,u){var H,c,m=this;m._93db(P),m.each(function(e){m._56I[e._id]&&(H=m.getDataUI(e),H&&(c=H._79o(),(!u||I.intersectsRect(u,c))&&H._42(P)))}),m._92db(P)},t.enableFlow=function(K){var g=this,F=g.dm(),R=F.$3e;g.$7e==A&&(R.forEach(function($){var Y=g.getDataUI($);Gc(Y)}),g.$7e=setInterval(function(){F.$3e.forEach(function(p){Pq(g.getDataUI(p))}),g.$9e()},K||g.$11e||50))},t.disableFlow=function(){var e=this;clearInterval(e.$7e),delete e.$7e;var I=e.dm().$3e;I&&e.$9e()},B.$5e=function(d,n,G,q,u){var b=this,F=b._data,T=b.gv,X=F.s(r),S=F.s(J),m=F.s(p),E=F.s(P),C=T.$8e,l=F.s(V);if(C==A&&(C=T.$8e={}),d.beginPath(),l!=A){var o=I.getImage(l),t=G/2;d.translate(n.x,n.y),d.rotate(u),d.translate(-n.x,-n.y),I.drawImage(d,o,n.x-t,n.y-t,G,G,F),d.translate(n.x,n.y),d.rotate(-u),d.translate(-n.x,-n.y)}else d.fillStyle=X,d.arc(n.x,n.y,G/2,0,M,!0),d.fill();if(E){var R=22,K=R+"_"+S+"_"+m,B=C[K];if(B==A){var D=_k("canvas");c.setCanvas(D,R,R);var Q=D.getContext("2d"),U=R/2,y=U,H=U;c.translateAndScale(Q,0,0,1),Q.beginPath();var e=Q.createRadialGradient(y,H,0,y,y,U);e.addColorStop(0,S),e.addColorStop(1,m),Q.fillStyle=e,Q.arc(y,H,U,0,M,!0),Q.fill(),B=C[K]=D}var t=q/2;I.drawImage(d,B,n.x-t,n.y-t,q,q,F)}},C._80o=function(l){h.call(this,l);var L=this,Y=L._data,b=L.gv;Y.s(q)&&b.$7e!=A&&of.call(L,l)},F._80o=function(H){o.call(this,H);var E=this,g=E._data,a=E.gv;g.s(q)&&a.$7e!=A&&of.call(E,H)}}("undefined"!=typeof global?global:"undefined"!=typeof self?self:"undefined"!=typeof window?window:(0,eval)("this"),Object);