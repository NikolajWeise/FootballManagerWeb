// Define the namespace
var componentsjs = componentsjs || {};

componentsjs.Canvas = function(element) {
	this.element = element;
	this.canvas;
	this.width;
	var that = this; // freaky js, cannot use "this.*" in functions inside this.function()

	this.init = function(height, width) {
	    this.width = width.substring(0, width.indexOf("px"));
	    this.element.innerHTML = "<canvas height='" + height + "' width='" + width + "'></canvas>";
	    this.canvas = this.element.getElementsByTagName("canvas")[0];
	};

	this.setPlay = function(play) {
	    if(play == null) {
	        return;
	    }
		var c = this.canvas.getContext("2d");
		c.lineWidth = 3;

		for(var i = 0; i < play.actions[0].length; i++) { // iterate over PositionWrappers
			var positionWrapper = play.actions[0][i];
			var actionList = play.actions[1][i];

			var startWidth = positionWrapper.fieldPosition.width;
			var startHeight = positionWrapper.fieldPosition.height;

			for(var a = 0; a < actionList.length; a++) { // iterate over Actions per PositionWrapper
			    var action = actionList[a];

			    // complex routes
			    if(action.actionType == "RCV_RUN" || action.actionType == "CRY_RUN") {
			        if(a < actionList.length - 1) {
        			    drawRun(c, startWidth, startHeight,
        			            action.direction.width, action.direction.height,
        			            (action.actionType == "CRY_RUN" || action.primaryAction));
			        } else {
			            drawRunEnd(c, startWidth, startHeight,
			                    action.direction.width, action.direction.height,
                                (action.actionType == "CRY_RUN" || action.primaryAction));
			        }
			    }
			    // counter run
			    else if(action.actionType == "CRY_COUNTER") {
			        var toRightSide = action.direction.width > this.width / 2;
			        var endWidth = toRightSide ? startWidth - 20 : startWidth + 20;
			        var endHeight = startHeight - 20;
			        // draw in opposite direction
			        drawRun(c, startWidth, startHeight, endWidth, endHeight, true);
			        startWidth = endWidth;
			        startHeight = endHeight;
			        endWidth = toRightSide ? endWidth + 30 : endWidth - 30;
			        endHeight = endHeight - 5;
			        // draw in opposite-opposite direction
			        drawRun(c, startWidth, startHeight, endWidth, endHeight, true);
			        // finally draw to given direction
                    drawRunEnd(c, endWidth, endHeight,
                            action.direction.width, action.direction.height,
                            true);
			    }
			    // predefined receive routes
			    else if(action.actionType.indexOf("RCV_") == 0) {
			        drawPredefinedRoute(c, action.actionType, startWidth, startHeight,
                            action.direction.width, action.direction.height,
                            (startWidth > this.width / 2), action.primaryAction);
                }
			    // block routes
			    else if(action.actionType.indexOf("BLOCK") > 0) {
			        if(a < actionList.length - 1) {
			            drawBlock(c, startWidth, startHeight,
			                    action.direction.width, action.direction.height);
			        } else {
			            drawBlockEnd(c, startWidth, startHeight,
			                    action.direction.width, action.direction.height);
			        }
			    } else  if(action.actionType == "BLITZ") {
			        if(a < actionList.length - 1) {
			            drawRun(c, startWidth, startHeight,
                                action.direction.width, action.direction.height);
                    } else {
                        drawRunEnd(c, startWidth, startHeight,
                                action.direction.width, action.direction.height);
                    }
			    } else  if(action.actionType == "ZONE") {
			        drawZone(c, startWidth, startHeight,
                            action.direction.width, action.direction.height);
			    } else { // man-to-man
			        // show nothing
			    }

			    startWidth = action.direction.width;
	            startHeight = action.direction.height;
			}
		}

		function drawPredefinedRoute(c, actionType, fromx, fromy, tox, toy, rightSide, primary) {
            drawRun(c, fromx, fromy, tox, toy, primary);
            var modx = 0;
            var mody = 0;
            if(actionType == "RCV_HOOK") {
                modx = rightSide ? -10 : 10;
                mody = 15;
            } else if(actionType == "RCV_COMEBACK") {
                modx = rightSide ? 40 : -40;
                mody = 15;
            } else if(actionType == "RCV_IN") {
                modx = rightSide ? -100 : 100;
                mody = 0;
            } else if(actionType == "RCV_OUT") {
                modx = rightSide ? 100 : -100;
                if(fromx < 100 || fromx > that.width - 100) { // that.width := this.width
                    modx /= 2;
                }
                mody = 0;
            } else if(actionType == "RCV_SLANT") {
                modx = rightSide ? -60 : 60;
                mody = rightSide ? modx : -modx;
            } else if(actionType == "RCV_FLAG") {
                modx = rightSide ? 40 : -40;
                mody = rightSide ? -modx : modx;
            }
            drawRunEnd(c, tox, toy, tox + modx, toy + mody, primary);
		}

        function drawRunEnd(c, fromx, fromy, tox, toy, primary) {
            c.strokeStyle = primary ? "#FF0000" : "#FFC800";
            drawArrow(c, fromx, fromy, tox, toy, 6);
        }

        function drawRun(c, fromx, fromy, tox, toy, primary) {
            c.strokeStyle = primary ? "#FF0000" : "#FFC800";
            c.beginPath();
            c.moveTo(fromx, fromy);
            c.lineTo(tox, toy);
            c.stroke();
        }

        function drawBlock(c, fromx, fromy, tox, toy, run) {
            c.strokeStyle = "#CCCCCC";
            c.beginPath();
            c.moveTo(fromx, fromy);
            c.lineTo(tox, toy);
            c.stroke();
        }

        function drawBlockEnd(c, fromx, fromy, tox, toy) {
            c.strokeStyle = "#CCCCCC";
            drawArrow(c, fromx, fromy, tox, toy, 2);
        }

        function drawZone(c, fromx, fromy, tox, toy) {
            var r = 0;
            var g = 0;
            var b = 0;
            if(toy < 100) {
                r = 153; g = 217; b = 234;
            } else if(toy < 300) {
                r = 255; g = 255; b = 0;
            } else if(tox < 100 || tox > that.width - 100) {
                r = 64; g = 0; b = 128;
            } else {
                r = 0; g = 0; b = 255;
            }

            c.strokeStyle = "rgb("+r+", "+g+", "+b+")";
            c.beginPath();
            c.moveTo(fromx, fromy);
            c.lineTo(tox, toy);
            c.stroke();

            drawCircle(c, tox, toy, r, g, b);
        }

		function drawArrow(c, fromx, fromy, tox, toy, arrowAngleOffset) {
	        c.beginPath();
	        var headlen = 12;
	        var angle = Math.atan2(toy - fromy, tox - fromx);
	        c.moveTo(fromx, fromy);
	        c.lineTo(tox, toy);
	        c.lineTo(tox - headlen * Math.cos(angle - Math.PI/arrowAngleOffset),
	                toy - headlen * Math.sin(angle - Math.PI/arrowAngleOffset));
	        c.moveTo(tox, toy);
	        c.lineTo(tox - headlen * Math.cos(angle + Math.PI/arrowAngleOffset),
	                toy - headlen * Math.sin(angle + Math.PI/arrowAngleOffset));
	        c.stroke();
	    }

		function drawCircle(c, x, y, r, g, b) {
		    var gradient = c.createRadialGradient(x, y, 0, x, y, 60);
	        gradient.addColorStop(0, "rgb("+r+", "+g+", "+b+")");
	        gradient.addColorStop(1, "rgba("+r+", "+g+", "+b+", 0.3)");

	        c.beginPath();
	        c.fillStyle = gradient;
	        c.strokeStyle = "rgba(255,255,255,0)";
	        c.arc(x, y, 60, 0, 2*Math.PI);
	        c.fill();
	        c.stroke();
		}
	};
};
