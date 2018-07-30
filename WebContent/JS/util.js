/**
 * 
 */

function getSystemTimeStr(){
	var now = new Date();
	var timeStr = now.getFullYear() + "-" + (now.getMonth()+1) + "-" + now.getDate() + " "
	+ now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
	return timeStr;
}

