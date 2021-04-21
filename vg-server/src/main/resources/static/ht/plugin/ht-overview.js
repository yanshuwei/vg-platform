!function(W,F){"use strict";var w="ht",p=w+".graph.",Y=W[w],P=Y.graph,E=Y.Default,H=Y.Color,D=null,M="px",G=E.getInternal(),q=G.getPinchDist,C=E.preventDefault,S=E.getTouchCount,k=E.startDragging;G.addMethod(E,{overviewBackground:H.widgetBackground,overviewMaskBackground:H.transparent,overviewContentBorderColor:H.widgetBorder,overviewContentBackground:H.background},!0),P.Overview=function(k){var x=this,s=x._view=G.createView(1,x);x._canvas=G.createCanvas(s),s.style.background=E.overviewBackground,s.appendChild(x._mask=G.createDiv()),x.setGraphView(k),x.addListeners()},E.def(p+"Overview",F,{ms_v:1,ms_fire:1,ms_listener:1,ms_ac:["maskBackground","contentBorderColor","contentBackground","autoUpdate","fixToRect"],_autoUpdate:!0,_fixToRect:!1,_rate:1,_scrollRect:{x:0,y:0,width:0,height:0},_maskBackground:E.overviewMaskBackground,_contentBorderColor:E.overviewContentBorderColor,_contentBackground:E.overviewContentBackground,getGraphView:function(){return this.gv},setGraphView:function(e){var D=this;if(D.gv!==e){var u=D.gv;D.gv=e,u&&(u.removeViewListener(D.handleGraphViewChanged,D),u.ump(D.handleGraphViewPropertyChanged,D)),e&&(e.addViewListener(D.handleGraphViewChanged,D),e.mp(D.handleGraphViewPropertyChanged,D)),D.fp("graphView",u,e),D.redraw()}},getCanvas:function(){return this._canvas},getMask:function(){return this._mask},dispose:function(){this.setGraphView(null)},onPropertyChanged:function(){this.redraw()},handleGraphViewChanged:function(D){this._autoUpdate&&"validate"===D.kind&&this.redraw()},handleGraphViewPropertyChanged:function(E){("canvasBackground"===E.property||this.getFixToRect()&&("zoom"===E.property||"translateX"===E.property||"translateY"===E.property))&&this.redraw()},redraw:function(){var j=this;j._redraw||(j._redraw=1,j.iv(50))},validateImpl:function(){var v=this,f=v.gv,r=v._canvas,V=v.getWidth(),U=v.getHeight(),Y=v._redraw;if(f){var W=v._mask,m=W.style,d=f.getViewRect(),c=this.getFixToRect(),z=c?"boolean"==typeof c?f.getContentRect():c:f.getScrollRect(),Q=z.x,g=z.y,y=z.width,T=z.height,N=v._rate=Math.max(y/V,T/U),n=v._x=(V-y/N)/2,o=v._y=(U-T/N)/2;if(0!==d.width&&0!==d.height||v.hasRetry||(E.callLater(v.iv,v,D),v.hasRetry=!0),(V!==r.clientWidth||U!==r.clientHeight)&&(G.setCanvas(r,V,U),Y=1),G.isSameRect(z,v._scrollRect)||(v._scrollRect=z,Y=1),Y){var _=G.initContext(r),S=f.getDataModel().getBackground()||v._contentBackground,Z=v._contentBorderColor,j=E.devicePixelRatio;_.clearRect(0,0,V*j,U*j),S&&G.fillRect(_,n*j,o*j,y/N*j,T/N*j,S),G.translateAndScale(_,-Q/N+n,-g/N+o,1/N),f._42(_),_.restore(),Z&&G.drawBorder(_,Z,n*j,o*j,y/N*j,T/N*j)}m.background=v._maskBackground,m.left=n+(d.x-Q)/N+M,m.top=o+(d.y-g)/N+M,m.width=d.width/N+M,m.height=d.height/N+M,v._redraw=null}else if(Y){var _=G.initContext(r);_.clearRect(0,0,V,U),_.restore(),v._redraw=null}},center:function(G){var P=this,N=P.gv;if(N){var f=N._zoom,e=N._29I,g=P._rate,y=P._scrollRect,L=E.getLogicalPoint(G,P._canvas),o=y.x+(L.x-P._x)*g,c=y.y+(L.y-P._y)*g;N.setTranslate((e.width/2-o)*f,(e.height/2-c)*f)}},handle_mousedown:function(M){this.handle_touchstart(M)},handleWindowMouseUp:function(G){this.handleWindowTouchEnd(G)},handleWindowMouseMove:function(z){this.handleWindowTouchMove(z)},handle_mousewheel:function(I){this.handleScroll(I,I.wheelDelta)},handle_DOMMouseScroll:function(w){2===w.axis&&this.handleScroll(w,-w.detail)},handleScroll:function(U,r){if(C(U),this.gv){var a=this.gv;r>0?a.scrollZoomIn():0>r&&a.scrollZoomOut()}},handle_touchstart:function(z){if(C(z),this.gv&&E.isLeftButton(z)){var T=this,H=T.gv,r=S(z);1===r?E.isDoubleClick(z)&&H.isResettable()?H.reset():(T.center(z),k(T,z)):2===r&&(T._dist=q(z),k(T,z))}},handleWindowTouchEnd:function(){delete this._dist},handleWindowTouchMove:function(x){if(this.gv){var i=this,h=i._dist,k=S(x);1===k?i.center(x):2===k&&h!=D&&i.gv.handlePinch(D,q(x),h)}}})}("undefined"!=typeof global?global:"undefined"!=typeof self?self:"undefined"!=typeof window?window:(0,eval)("this"),Object);