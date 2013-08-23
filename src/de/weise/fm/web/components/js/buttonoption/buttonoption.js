// Define the namespace
var componentsjs = componentsjs || {};

componentsjs.ButtonOption = function(element) {
	this.element = element;
	this.activeItem = "";

	var component = this;
	var div = this.element;

	this.setActiveItem = function(activeItem) {
	    component.activeItem = activeItem;

	    var buttons = div.getElementsByTagName("button");
	    for(var i = 0 ; i < buttons.length; i++) {
	        var btn = buttons[i];
	        if(activeItem == btn.firstChild.textContent) {
	            var cssClass = btn.getAttribute("class");
	            if(cssClass.indexOf("active") < 0) {
	                cssClass += " active";
	            }
	            btn.setAttribute("class", cssClass);
	            component.click();
	        } else {
	            var cssClass = btn.getAttribute("class");
	            cssClass = cssClass.replace("active", "");
	            btn.setAttribute("class", cssClass);
	        }
	    }
	};

    this.getActiveItem = function() {
        return component.activeItem;
    };

	this.addItem = function(text, styles) {
        var btn = document.createElement("BUTTON");
        btn.appendChild(document.createTextNode(text));
        btn.setAttribute("type", "submit");
        var cssClass = "btn ";
        if(styles != undefined) {
            for(var i = 0; i < styles.length; i++) {
                cssClass += styles[i] + " ";
            }
        }
        btn.setAttribute("class", cssClass);
        btn.onclick = function() {
            component.setActiveItem(text);
        };
        div.appendChild(btn);
	};

	this.removeItems = function() {
	    var buttons = div.getElementsByTagName("button");
	    for(var i = buttons.length -1 ; i >= 0; i--) {
	        div.removeChild(buttons[i]);
	    }
	};

	this.click = function() {
	};
};
