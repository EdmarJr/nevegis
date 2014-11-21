function createXMLHttpRequest() {
	if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
		try { return new ActiveXObject("Msxml2.XMLHTTP.6.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP.3.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP") } catch(e) {}
		try { return new ActiveXObject("Microsoft.XMLHTTP") } catch(e) {}
		throw new Error( "This browser does not support XMLHttpRequest." )
	};
	return new XMLHttpRequest();
}

var _AJAX = createXMLHttpRequest();
var _callbackFn;

function receiveValue() {
	if(_AJAX.readyState == 4 && _AJAX.status == 200) {
		var myJsonObj = json_parse(_AJAX.responseText);
		if (myJsonObj.result && myJsonObj.result == 'ERR') {
			throw new Error('Erro');
		} else if (myJsonObj.err) {
			throw new Error(myJsonObj.err);
		} else {
			_callbackFn(myJsonObj);
		}
	}else if (_AJAX.readyState == 4 && _AJAX.status != 200) {
		throw new Error('Erro');
	}
}

function sendAjaxValue(server, paramName, value, callback) {
	_callbackFn = callback;
	_AJAX.onreadystatechange = receiveValue;
    if (server.indexOf('?') > 0)
        _AJAX.open("GET", server + "&" + paramName + "=" + value);
    else
        _AJAX.open("GET", server + "?" + paramName + "=" + value);
	_AJAX.send("");
}
