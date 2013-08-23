window.de_weise_fm_web_components_js_buttonoption_ButtonOption = function() {

    this.activeOption = "";

    var buttonOption = new componentsjs.ButtonOption(this.getElement());
    var connector = this;

	// Handle changes from the server-side
	this.onStateChange = function() {
	    if(this.getState().dirty) {
    	    buttonOption.removeItems();

    	    var items = this.getState().items;
    	    for(var i = 0; i < items.length; i++) {
    	        buttonOption.addItem(items[i], this.getState().styles);
    	    }

    	    connector.markAsClean();
	    } else {
    	    var newActiveItem = this.getState().activeItem;
    	    connector.activeItem = newActiveItem;
            buttonOption.setActiveItem(newActiveItem);
	    }
	};

	// Pass user interaction to the server-side
    buttonOption.click = function() {
        connector.activeItem = buttonOption.getActiveItem();
        connector.onClick(connector.activeItem);
    };
};
