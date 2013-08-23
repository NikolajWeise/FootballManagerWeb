window.de_weise_fm_web_components_js_canvas_Canvas = function() {

    var canvas = new componentsjs.Canvas(this.getElement());

	// Handle changes from the server-side
	this.onStateChange = function() {
	    canvas.init(this.getState().height, this.getState().width);
		canvas.setPlay(this.getState().play);
	};
};
