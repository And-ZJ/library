/**
 * 
 */
function GetQueryString(name) {
    	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    	   var r = window.location.search.substr(1).match(reg);
    	   if (r!=null) return (r[2]); return null;
    	}
function formCallback(result, form) {
    window.status = "valiation callback for form '" + form.id + "': result = " + result;
}