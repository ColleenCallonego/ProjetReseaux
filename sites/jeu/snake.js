grid = new Array();
LEFT = 37;
UP = 38;
RIGHT = 39;
DOWN = 40;
goodSnake = new snake(20,15);
var inter;
badSnakes = new Array();
var nbm = 0;

$(document).ready(function(){
	buildGrid();
	draw();
	inter = setInterval(updateGrid, 80);
	setInterval(addBadSnake, 500);

document.addEventListener('keydown', function(e){
	goodSnake.setDirection(e.which);
})

});



function buildGrid(){
	var $board = $("#board");
	for(var i = 0; i < 40; i++){
		grid[i] = new Array();
	}
	for(var j = 0; j < 30; j++){
		for(var i = 0; i < 40; i++){
			var $cell = $("<div>");
			$board.append($cell);
			grid[i][j] = $cell;
		}
	}
}

function updateGrid(){
	document.getElementById("scoreSpan").innerHTML = nbm;
	for (var i = badSnakes.length - 1; i >= 0; i--){
		if(badSnakes[i].getDirection() == 38 && badSnakes[i].head[1] == 0 || badSnakes[i].getDirection() == 40 && badSnakes[i].head[1] == 29 || badSnakes[i].getDirection() == 37 && badSnakes[i].head[0] == 0 || badSnakes[i].getDirection() == 39 && badSnakes[i].head[0] == 39){
			badSnakes.splice(i, 1);
		}
		for(var j = badSnakes.length - 1; j >= 0; j--){
			if(i != j){
				if(bites(badSnakes[i], badSnakes[j])){
					badSnakes.splice(j, 1);
				}
			}
		}
	}
	if(goodSnake.getDirection() == 38 && goodSnake.head[1] == 0 || goodSnake.getDirection() == 40 && goodSnake.head[1] == 29 || goodSnake.getDirection() == 37 && goodSnake.head[0] == 0 || goodSnake.getDirection() == 39 && goodSnake.head[0] == 39){
		clearInterval(inter);
	}
	else{
		move(goodSnake);
		for (var i = 0; i < badSnakes.length; i++){
			changeDirection(badSnakes[i]);
			move(badSnakes[i]);
		}
		for(var i = badSnakes.length - 1; i >= 0; i--){
			if(bites(goodSnake, badSnakes[i])){
				nbm++;
				badSnakes.splice(i,1);
			}
		}
		for(var i = badSnakes.length - 1; i >= 0; i--){
			if (bites(badSnakes[i], goodSnake)){
				clearInterval(inter);
			}
		}
		draw();
	}
}

function snake(x, y){
	this.head = [x, y];
	this.tail = [[x, y], [x, y], [x, y], [x, y], [x, y]];
	var direction = entierAleatoire(37,40);
	this.getDirection = function(){
		return direction;
	}
	this.setDirection = function(d){
		if (Math.abs(direction - d) != 2){
			direction = d;
		}
	}
}

function addBadSnake(){
	badSnakes.unshift(new snake(entierAleatoire(0, 39),entierAleatoire(0, 29)));
}

function supBadSnake(i){
	
}

function changeDirection(snake){
	var alea = entierAleatoire(0, 9);
	if(alea == 1){
		snake.setDirection(entierAleatoire(37, 40));
	}
}

function entierAleatoire(min, max)
{
 return Math.floor(Math.random() * (max - min + 1)) + min;
}

function draw(){
	$("#board > div").css("background-color", "black");
	for (var i = 0; i < 5; i++){
		grid[goodSnake.tail[i][0]][goodSnake.tail[i][1]].css("background-color", "yellow");
	}
	grid[goodSnake.head[0]][goodSnake.head[1]].css("background-color", "red");
	for(var j = 0; j < badSnakes.length; j++){
		for(var k = 0; k < 5; k++){
			grid[badSnakes[j].tail[k][0]][badSnakes[j].tail[k][1]].css("background-color", "blue");
		}
		grid[badSnakes[j].head[0]][badSnakes[j].head[1]].css("background-color", "green");
	}
}

function move(snake){
	snake.tail[4] = snake.tail[3];
	snake.tail[3] = snake.tail[2];
	snake.tail[2] = snake.tail[1];
	snake.tail[1] = snake.tail[0];
	snake.tail[0] = snake.head;
	if(snake.getDirection() == 37){
		snake.head = [snake.head[0] - 1, snake.head[1]];
	}
	if(snake.getDirection() == 38){
		snake.head = [snake.head[0], snake.head[1] - 1];
	}
	if(snake.getDirection() == 39){
		snake.head = [snake.head[0] + 1, snake.head[1]];
	}
	if(snake.getDirection() == 40){
		snake.head = [snake.head[0], snake.head[1] + 1];
	}
}

function bites(s1, s2){
	for(var i = 0; i < 5; i++){
		if (s1.head[0] == s2.tail[i][0] && s1.head[1] == s2.tail[i][1]){
			return true;
		}
	}
	return false;
}