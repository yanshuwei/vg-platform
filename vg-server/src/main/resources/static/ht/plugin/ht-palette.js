!function(n,T,a){"use strict";var t="px",S="0",g="innerHTML",l="className",u=ht.Default,P=ht.Color,o=ht.Node,R="position",v="top",r="left",z=u.animate,f=u.getInternal(),c="width",S="0",D="none",j="max-height",A="font",N="background",X="border-box",K="user-select",p="box-sizing",H="overflow",C=u.isTouchable,f=u.getInternal(),k=P.titleIconBackground,M=u.scrollBarInteractiveSize,w=/msie 9/.test(n.navigator?n.navigator.userAgent.toLowerCase():""),Y=null,I=function(){return document},i=function(C){return I().createElement(C)},s=function(){return i("div")},e=function(){var t=s(),_=t.style;return _.msTouchAction=D,_.cursor="default",C&&_.setProperty("-webkit-tap-highlight-color","rgba(0, 0, 0, 0)",Y),_.position="absolute",_.left=S,_.top=S,t},W=function(){return i("canvas")},x=function(){return document.body},B=function(B,k,v){B.style.setProperty(k,v,Y)},V=function(B,y){B.style.removeProperty(y)},Z=function(A,s,M){u.def(ht.widget[A],s,M)},_=function(d,C){d.appendChild(C)},L=function(h,s){h.removeChild(s)},m=u.eventListenerOptionsFalse,Q=(u.eventListenerOptionsTrue,function(u,J,P,T){u.addEventListener(J,P,T?m:m)});f.addMethod(u,{paletteExpandIcon:{width:16,height:16,comps:[{type:"triangle",rect:[4,4,10,8],background:k,rotation:3.14}]},paletteCollapseIcon:{width:16,height:16,comps:[{type:"triangle",rect:[4,4,10,8],background:k}]},paletteTitleLabelColor:u.labelSelectColor,paletteTitleLabelFont:u.labelFont,paletteContentLabelFont:u.labelFont,paletteContentLabelColor:"#777",paletteContentBackground:"#fff",paletteTitleHeight:u.widgetTitleHeight,paletteTitleBackground:P.titleBackground,paletteTitleHoverBackground:P.titleBackground,paletteSeparatorWidth:1,paletteSeparatorColor:a,paletteItemHoverBorderColor:P.highlight,paletteItemSelectBackground:P.highlight},!0);var y=".palette-item:hover{border: 1px solid "+u.paletteItemHoverBorderColor+" !important}"+" .palette-header:hover{background: "+u.paletteTitleHoverBackground+" !important}",q=document.createElement("style");C||(q.styleSheet?q.styleSheet.cssText=y:q.appendChild(I().createTextNode(y))),I().getElementsByTagName("head")[0].appendChild(q);var F=function(U){var i=this;i.$22h=U,i.addListeners()};u.def(F,T,{ms_listener:1,getView:function(){return this.$22h.getView()},$26h:function(){var U=this;U.$36h&&x().removeChild(U.$36h),U.$23h=U.$24h=U.$25h=U.$35h=U.$36h=Y},handle_touchstart:function(d){for(var p,V=this,e=V.$22h,T=d.target,J=e.sm(),I=e.dm(),o="palette-header",F="palette-header-tool",B="palette-item",$=!1,A=!1,C=!1;T&&(T[l]||"").indexOf(o)<0&&(T[l]||"").indexOf(B)<0;)T=T.parentNode;if(T&&T[l].indexOf(F)>=0?$=!0:T&&T[l].indexOf(o)>=0?C=!0:T&&T[l].indexOf(B)>=0&&(A=!0),u.isLeftButton(d))if(V.$27h(d))V.$24h=u.getClientPoint(d),V.$25h=e.ty();else if($){u.preventDefault(d),p=T.parentNode.$11h;var N=I.getDataById(p),a=N.s("tools")[T.toolIndex];a.action&&a.action.call(e)}else if(C){u.preventDefault(d),p=T.$11h;var N=I.getDataById(p);N.isExpanded()?N.setExpanded(!1):N.setExpanded(!0)}else if(A){p=T.$11h;var D=I.getDataById(p);J.ss(D),e.handleDragAndDrop&&(u.preventDefault(d),D.s("draggable")&&(e.handleDragAndDrop(d,"prepare"),V.$35h=0)),D.s("draggable")||(u.preventDefault(d),V.$24h=u.getClientPoint(d),V.$25h=e.ty())}else u.preventDefault(d),V.$24h=u.getClientPoint(d),V.$25h=e.ty();else V.$26h(d)},handle_mousedown:function(t){this.handle_touchstart(t)},handle_mousewheel:function(F){this.handleScroll(F,F.wheelDelta/40,F.wheelDelta!==F.wheelDeltaX)},handle_DOMMouseScroll:function(h){this.handleScroll(h,-h.detail,1)},handleScroll:function(a,F,P){var W=this.$22h;u.preventDefault(a),P&&W._41o()&&W.ty(W.ty()+20*F)},handle_mouseup:function(q){this.handle_touchend(q)},handle_touchend:function(k){var g=this;g.$37h(k),g.$26h(k)},handleWindowMouseUp:function(C){this.handleWindowTouchEnd(C)},handleWindowTouchEnd:function(K){var W=this;W.$37h(K),W.$26h(K)},$37h:function(o){var R=this,d=R.$22h;2===R.$35h&&(R.$35h=3,d.handleDragAndDrop(o,"end"))},handleWindowMouseMove:function(x){this.handleWindowTouchMove(x)},handleWindowTouchMove:function(d){var E=this,Z=E.$22h,V=E.$23h,g=E.$24h,f=E.$25h,p=u.getClientPoint(d),c=Z._29I,l=E.$36h;if(1===E.$35h||2===E.$35h){if(E.$35h=2,Z.handleDragAndDrop(d,"between"),C){var n=d.touches[0];d=n?n:d.changedTouches[0]}l.style.left=d.pageX-parseInt(l.width)/2+t,l.style.top=d.pageY-parseInt(l.height)/2+t}else"p"===V?Z.ty(f+p.y-g.y):"v"===V&&Z.ty(f+(g.y-p.y)/c.height*Z._59I)},handle_mousemove:function(e){this.handle_touchmove(e)},handle_touchmove:function(S){if(!u.isDragging()&&u.isLeftButton(S)){var z=this,n=z.$22h,m=z.$27h(S);if(z.$24h){if(!z.$23h){if(u.getDistance(u.getClientPoint(S),z.$24h)<2)return;z.$23h=m?"v":"p",u.startDragging(z)}}else if(m)n._43o();else if(0===z.$35h){if(z.$35h=1,n.handleDragAndDrop(S,"begin"),C){var E=S.touches[0];S=E?E:S.changedTouches[0]}var H=z.$36h=new Image,Q=n.$10h[n.sm().ld().getId()].querySelector(".image-box"),h=parseInt(Q.style.width),K=parseInt(Q.style.height);H.draggable=!1,H.src=Q.toDataURL(),H.width=h,H.height=K,H.style.position="absolute",H.style.left=S.pageX-h/2+t,H.style.top=S.pageY-K/2+t,x().appendChild(H),u.startDragging(z)}}},$27h:function(Q){var L=this.$22h,S=L.getView(),p=S.getBoundingClientRect(),t=L._29I,A=Q.clientX-p.left+S.scrollLeft;return L._41o()&&t.x+t.width-A<M}}),ht.widget.Palette=function(h){var W=this,M=W._view=f.createView(null,W);W.$9h={},W.$10h={},W.$4h={},W._29I={x:0,y:0,width:0,height:0},W._59I=0,W.dm(h?h:new ht.DataModel),M[l]="ht-widget-palette",W.$29h=new F(W),B(M,N,u.paletteContentBackground),B(M,H,"auto"),B(M,p,X),B(M,"-moz-"+p,X),B(M,"-webkit-"+K,D),B(M,"-moz-"+K,D),B(M,"-ms-"+K,D),B(M,K,D),B(M,"position","absolute"),B(M,"overflow","hidden"),_(M,W._79O=e()),Q(M,"dragstart",function(T){T.dataTransfer&&(T.dataTransfer.setData("Text","nodeid:"+T.target.$11h),T.dataTransfer.effectAllowed="all",W.$29h.$26h())})},Z("Palette",T,{ms_v:1,ms_fire:1,ms_dm:1,ms_sm:1,ms_vs:1,ms_ac:["itemImageWidth","itemImageHeight","itemImagePadding","itemMargin","layout","autoHideScrollBar","scrollBarSize","scrollBarColor"],$30h:0,_itemImagePadding:4,_itemImageWidth:70,_itemImageHeight:50,_itemMargin:10,_layout:"largeicons",_autoHideScrollBar:u.autoHideScrollBar,_scrollBarSize:u.scrollBarSize,_scrollBarColor:u.scrollBarColor,getViewRect:function(){return this._29I},ty:function(E){return E==Y?this.getTranslateY():(this.setTranslateY(E),void 0)},setTranslateY:function(k){if(this.$32h==Y){var b=this,w=b.$33h(k),g=b.$30h;b.$30h=w,b.fp("translateY",g,w)}},getTranslateY:function(){return this.$30h},setLayout:function(W){var l,y,D=this,d=D._layout;D._layout=W,"smallicons"===W?l=y=20:"iconsonly"===W?l=y=50:(l=70,y=50),D.setItemImageWidth(l),D.setItemImageHeight(y),D.setItemImagePadding(4),D.fp("layout",d,W)},getDataAt:function(b){for(var q=b.target;q&&q.$11h==Y;)q=q.parentNode;return q&&q.$11h!=Y?this.getDataModel().getDataById(q.$11h):void 0},$20h:function(){var L=16;return C&&(L*=1.2),L},$19h:function(){return u.paletteTitleHeight},$18h:function(){var k=u.paletteSeparatorWidth,F=u.paletteTitleBackground,H=u.paletteSeparatorColor||u.brighter(F);return k+t+" solid "+H},$17h:function(R){B(R,"cursor","pointer"),B(R,"display","inline-block"),B(R,"margin-right",(C?8:4)+t),B(R,"vertical-align",v)},$1h:function(z){var L=this,Z=s(),m=s(),e=i("span");Z[l]="palette-header",B(Z,R,"relative"),B(Z,N,u.paletteTitleBackground),B(Z,"color",u.paletteTitleLabelColor),B(Z,v,S),B(Z,p,X),B(Z,"-moz-"+p,X),B(Z,"padding","0 5px 0 0"),B(Z,"border-top",L.$18h()),B(Z,c,"100%"),B(Z,"cursor","pointer"),B(Z,"white-space","nowrap"),B(Z,H,"hidden"),B(Z,A,u.paletteTitleLabelFont),B(Z,"line-height",L.$19h()+t),Z.$11h=z.getId();var y=W(),I=L.$19h(),F=L.$20h();L.$17h(y),f.setCanvas(y,F,I),_(Z,y);var n=z.s("tools");if(n)for(var C=0;C<n.length;C++){var h=W();L.$17h(h),f.setCanvas(h,F,I),h[l]="palette-header-tool palette-header-tool"+z.getId()+"-"+C,h.style.position="absolute",h.style.right=(F+10)*C+"px",h.toolIndex=C,_(Z,h)}return y[l]="palette-toggle-icon-"+z.getId(),m[l]="palette-content",B(m,"max-height",0+t),B(m,A,u.paletteContentLabelFont),B(m,H,"hidden"),m.$11h=z.getId(),L.$9h[z.getId()]=m,e[g]=z.getName(),B(e,A,u.paletteTitleLabelFont),_(Z,y),_(Z,e),[Z,m]},$2h:function(T){var S=this,R=S._layout,e=w&&T.s("draggable")?i("a"):s(),d=W(),H=s(),V=T.getName()||"",Y=T.s("title")||T.getToolTip()||V,j=S._itemMargin;d[l]="image-box";var Z=S.getItemImageWidth(),A=S.getItemImageHeight();return f.setCanvas(d,Z,A),_(e,d),H[g]=V,H[l]="label-box","iconsonly"!==R&&_(e,H),e[l]="palette-item",B(e,"vertical-align",v),B(e,"cursor","pointer"),B(e,"border-radius",5+t),B(e,"border","1px solid transparent"),B(e,"text-align","center"),B(e,"display","inline-block"),B(e,"margin-left",j+t),B(e,"margin-top",j+t),B(e,"color",u.paletteContentLabelColor),"smallicons"===R?(B(d,"vertical-align","middle"),B(e,"margin-left",2+t),B(e,"margin-top",2+t),B(e,"padding",2+t),B(e,"text-align",r),B(H,"display","inline-block"),B(H,"min-width",S.$21h+S._itemMargin+t)):"largeicons"===R&&(B(H,"max-width",Z+t),B(H,"overflow","hidden")),e.$11h=T.getId(),Y&&(e.title=Y),T.s("draggable")&&!S.handleDragAndDrop&&(w?(e.href="#",B(e,"text-decoration",D)):e.draggable="true"),e},$16h:function(p,e,X,d){var x=f.initContext(p);f.translateAndScale(x,0,0,1),x.clearRect(0,0,X,X);var j=(X-d)/2;u.drawStretchImage(x,u.getImage(e),"fill",0,j,d,d),x.restore()},$15h:function(h){var d=this,D=h.getId(),Q=d._view.querySelector(".palette-toggle-icon-"+D),v=h.isExpanded()?u.paletteCollapseIcon:u.paletteExpandIcon;if(Q&&v){var C=d.$19h(),w=d.$20h();d.$16h(Q,v,C,w)}},_drawToolsIcon:function(u){var K=this,l=u.s("tools");if(l)for(var S=0;S<l.length;S++){var j=K._view.querySelector(".palette-header-tool"+u.getId()+"-"+S),F=l[S].icon,f=K.$19h(),c=K.$20h();K.$16h(j,F,f,c)}},$14h:function(W){var G=this,Z=W.getId(),L=G.$10h[Z].querySelector(".image-box"),j=W.getImage(),e=W.s("image.stretch");if(L&&j){var q=f.initContext(L),Y=G.getItemImagePadding();Y="smallicons"===G._layout?Y/2:Y;var k=G.getItemImageWidth()-2*Y,J=G.getItemImageHeight()-2*Y;f.translateAndScale(q,0,0,1),q.clearRect(0,0,k,J),u.drawStretchImage(q,u.getImage(j),e,Y,Y,k,J,W,G),q.restore()}},validateImpl:function(){var E,h,O,R=this,s=R.$9h,X=R._layout,G=R.$10h,x=R.$4h,K=R._view,k=R.dm();if(R.$13h&&(delete R.$13h,x={},k.each(function(N){x[N.getId()]=N})),"smallicons"===X)for(var F in x){var M=x[F];if(M instanceof o){var D=M.getName()||"",m=u.getTextSize(u.paletteContentLabelFont,D).width;R.$21h!=Y&&R.$21h>m||(R.$21h=m)}}for(var F in x){O=x[F];var I,d;if(k.contains(O)){if(O instanceof ht.Group){var c,J=R.$1h(O),e=G[O.getId()];e&&(c=e.nextSibling,L(K,c),L(K,e)),h=k.getSiblings(O).indexOf(O);var H=K.children[2*h]||R._79O;H&&H.parentNode?(K.insertBefore(J[0],H),K.insertBefore(c||J[1],H)):(K.appendChild(J[0]),K.appendChild(c||J[1])),G[O.getId()]=J[0],E=s[O.getId()]=c||J[1],d=O.$12h;var N=O.s("promptText");d||(O.$12h=i("div"),O.$12h[g]=N||"",d=O.$12h),0===O.getChildren().size()?E.contains(d)||_(E,d):E.contains(d)&&L(E,d)}else if(I=O.getParent()){var a=R.$2h(O),v=G[O.getId()];E=s[I.getId()],v&&L(v.parentNode,v),h=k.getSiblings(O).indexOf(O);var p=E.children[h];p?E.insertBefore(a,p):_(E,a),G[O.getId()]=a,R.$14h(O)}}else{var r=G[O.getId()],C=r.parentNode;if(O instanceof ht.Group){var y=r.nextSibling;L(K,r),L(K,y),delete s[O.getId()]}else L(C,r),0===C.children.length&&(I=k.getDataById(C.$11h),I&&(d=I.$12h,d&&!C.contains(d)&&_(C,d)));delete G[O.getId()]}}R.$4h={};var T=function(){var C=R._59I,Q=0;R.$32h!=Y&&(clearInterval(R.$32h),Q=0,delete R.$32h),R.$32h=setInterval(function(){R.$31h(),C===R._59I?(Q++,Q>=2&&(clearInterval(R.$32h),delete R.$32h)):(Q=0,C=R._59I)},30)};for(var l in s)if(E=s[l],O=k.getDataById(s[l].$11h),R.$15h(O),R._drawToolsIcon(O),O.isExpanded()){if(E.style.maxHeight===0+t){var n=E.scrollHeight+R._itemMargin+t;z(E).duration(200).set(j,n).set("padding-bottom",R._itemMargin+t).end(function(){return function(){T()}}(n))}else E.style.maxHeight=E.scrollHeight+t;E.style.paddingBottom=R._itemMargin+t}else E.style.maxHeight!==0+t&&z(E).duration(200).set(j,S).set("padding-bottom",S).end(function(){return function(){T()}}(E));R.$28h(),R.$31h()},$31h:function(){for(var Q=this,c=Q._view,y=0,m=c.children,E=0;E<m.length;E++){var s=m[E];s.className&&s.className.indexOf("palette-")>=0&&(y+=s.offsetHeight)}Q._59I=y,Q.$30h=Q.$33h(Q.ty());var n=Q.ty();c.scrollTop=-n,Q._29I={x:0,y:-n,width:c.clientWidth,height:c.clientHeight},B(Q._79O,v,-n+t),Q._93I()},$33h:function(C){var a=this,j=a._29I.height-a._59I;return j>C&&(C=j),C>0?0:Math.round(C)},redraw:function(){this.$13h||(this.$13h=1,this.iv())},onPropertyChanged:function(j){["autoHideScrollBar","scrollBarSize","scrollBarColor","translateY"].indexOf(j.property)<0&&this.redraw(),"translateY"===j.property&&(this.iv(),this._43o())},findDataByName:function(v){for(var e=this.dm().getDatas(),h=0;h<e.size();h++){var O=e.get(h);if(O.getName()===v)return O}},setDataModel:function(y){var h=this,e=h._dataModel,w=h._selectionModel;e!==y&&(e&&(e.umm(h.$6h,h),e.umd(h.$8h,h),e.umh(h.$7h,h),w||e.sm().ums(h.$28h,h)),h._dataModel=y,y.mm(h.$6h,h),y.md(h.$8h,h),y.mh(h.$7h,h),w?w._21I(y):y.sm().ms(h.$28h,h),h.sm().setSelectionMode("single"),h.fp("dataModel",e,y))},$6h:function(M){var b=this,q=b._view,R=M.data,E=b.$4h;"add"===M.kind?E[R.getId()]=R:"remove"===M.kind?E[R.getId()]=R:"clear"===M.kind&&(b.$10h={},b.$9h={},b.$4h={},q[g]=""),b.iv()},$7h:function(c){var k=this,L=c.data;k.$4h[L.getId()]=L,k.iv()},$8h:function(z){var Y=this,P=z.data,b=z.property;"expanded"===b?Y.iv():(Y.$4h[P.getId()]=P,Y.iv())},$28h:function(){var i,G=this,W=G.sm(),F="palette-item",k=W.ld();this.dm().each(function(R){i=G.$10h[R.getId()],i&&i[l].indexOf(F)>=0&&(R===k?B(i,N,u.paletteItemSelectBackground):V(i,N))})}})}("undefined"!=typeof global?global:"undefined"!=typeof self?self:"undefined"!=typeof window?window:(0,eval)("this"),Object);