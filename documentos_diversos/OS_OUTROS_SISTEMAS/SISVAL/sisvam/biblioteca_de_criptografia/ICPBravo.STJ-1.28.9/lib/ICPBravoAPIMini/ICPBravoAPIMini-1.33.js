// ------------------------------ json_parse.js ------------------------------

/*
    json_parse.js
    2012-06-20
*/

/*members "", "\"", "\/", "\\", at, b, call, charAt, f, fromCharCode,
    hasOwnProperty, message, n, name, prototype, push, r, t, text
*/

var json_parse = (function () {
    "use strict";

    // This is a function that can parse a JSON text, producing a JavaScript
    // data structure. It is a simple, recursive descent parser. It does not use
    // eval or regular expressions, so it can be used as a model for implementing
    // a JSON parser in other languages.

    // We are defining the function inside of another function to avoid creating
    // global variables.

    var at,     // The index of the current character
        ch,     // The current character
        escapee = {
            '"': '"',
            '\\': '\\',
            '/': '/',
            b: '\b',
            f: '\f',
            n: '\n',
            r: '\r',
            t: '\t'
        },
        text,

        error = function (m) {

            // Call error when something is wrong.

            throw {
                name: 'SyntaxError',
                message: m,
                at: at,
                text: text
            };
        },

        next = function (c) {

            // If a c parameter is provided, verify that it matches the current character.

            if (c && c !== ch) {
                error("Expected '" + c + "' instead of '" + ch + "'");
            }

            // Get the next character. When there are no more characters,
            // return the empty string.

            ch = text.charAt(at);
            at += 1;
            return ch;
        },

        number = function () {

            // Parse a number value.

            var number,
                string = '';

            if (ch === '-') {
                string = '-';
                next('-');
            }
            while (ch >= '0' && ch <= '9') {
                string += ch;
                next();
            }
            if (ch === '.') {
                string += '.';
                while (next() && ch >= '0' && ch <= '9') {
                    string += ch;
                }
            }
            if (ch === 'e' || ch === 'E') {
                string += ch;
                next();
                if (ch === '-' || ch === '+') {
                    string += ch;
                    next();
                }
                while (ch >= '0' && ch <= '9') {
                    string += ch;
                    next();
                }
            }
            number = +string;
            if (!isFinite(number)) {
                error("Bad number");
            } else {
                return number;
            }
        },

        string = function () {

            // Parse a string value.

            var hex,
                i,
                string = '',
                uffff;

            // When parsing for string values, we must look for " and \ characters.

            if (ch === '"') {
                while (next()) {
                    if (ch === '"') {
                        next();
                        return string;
                    }
                    if (ch === '\\') {
                        next();
                        if (ch === 'u') {
                            uffff = 0;
                            for (i = 0; i < 4; i += 1) {
                                hex = parseInt(next(), 16);
                                if (!isFinite(hex)) {
                                    break;
                                }
                                uffff = uffff * 16 + hex;
                            }
                            string += String.fromCharCode(uffff);
                        } else if (typeof escapee[ch] === 'string') {
                            string += escapee[ch];
                        } else {
                            break;
                        }
                    } else {
                        string += ch;
                    }
                }
            }
            error("Bad string");
        },

        white = function () {

            // Skip whitespace.

            while (ch && ch <= ' ') {
                next();
            }
        },

        word = function () {

            // true, false, or null.

            switch (ch) {
                case 't':
                    next('t');
                    next('r');
                    next('u');
                    next('e');
                    return true;
                case 'f':
                    next('f');
                    next('a');
                    next('l');
                    next('s');
                    next('e');
                    return false;
                case 'n':
                    next('n');
                    next('u');
                    next('l');
                    next('l');
                    return null;
            }
            error("Unexpected '" + ch + "'");
        },

        value,  // Place holder for the value function.

        array = function () {

            // Parse an array value.

            var array = [];

            if (ch === '[') {
                next('[');
                white();
                if (ch === ']') {
                    next(']');
                    return array;   // empty array
                }
                while (ch) {
                    array.push(value());
                    white();
                    if (ch === ']') {
                        next(']');
                        return array;
                    }
                    next(',');
                    white();
                }
            }
            error("Bad array");
        },

        object = function () {

            // Parse an object value.

            var key,
                object = {};

            if (ch === '{') {
                next('{');
                white();
                if (ch === '}') {
                    next('}');
                    return object;   // empty object
                }
                while (ch) {
                    key = string();
                    white();
                    next(':');
                    if (Object.hasOwnProperty.call(object, key)) {
                        error('Duplicate key "' + key + '"');
                    }
                    object[key] = value();
                    white();
                    if (ch === '}') {
                        next('}');
                        return object;
                    }
                    next(',');
                    white();
                }
            }
            error("Bad object");
        };

    value = function () {

        // Parse a JSON value. It could be an object, an array, a string, a number,
        // or a word.

        white();
        switch (ch) {
            case '{':
                return object();
            case '[':
                return array();
            case '"':
                return string();
            case '-':
                return number();
            default:
                return ch >= '0' && ch <= '9' ? number() : word();
        }
    };

    // Return the json_parse function. It will have access to all of the above
    // functions and variables.

    return function (source, reviver) {
        var result;

        text = source;
        at = 0;
        ch = ' ';
        result = value();
        white();
        if (ch) {
            error("Syntax error");
        }

        // If there is a reviver function, we recursively walk the new structure,
        // passing each name/value pair to the reviver function for possible
        // transformation, starting with a temporary root object that holds the result
        // in an empty key. If there is not a reviver function, we simply return the
        // result.

        return typeof reviver === 'function'
            ? (function walk(holder, key) {
                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }({ '': result }, ''))
            : result;
    };
}());

// ------------------------------ deployJava.js ------------------------------

var deployJava = function () {
    /** HTML attribute filter implementation */
    var hattrs = {
        core: [ 'id', 'class', 'title', 'style' ],
        i18n: [ 'lang', 'dir' ],
        events: [ 'onclick', 'ondblclick', 'onmousedown', 'onmouseup',
            'onmouseover', 'onmousemove', 'onmouseout', 'onkeypress',
            'onkeydown', 'onkeyup' ],
        applet: [ 'codebase', 'code', 'name', 'archive', 'object',
            'width', 'height', 'alt', 'align', 'hspace', 'vspace' ],
        object: [ 'classid', 'codebase', 'codetype', 'data', 'type',
            'archive', 'declare', 'standby', 'height', 'width', 'usemap',
            'name', 'tabindex', 'align', 'border', 'hspace', 'vspace' ]
    };

    var object_valid_attrs = hattrs.object.concat(hattrs.core, hattrs.i18n,
        hattrs.events);
    var applet_valid_attrs = hattrs.applet.concat(hattrs.core);
	
    function documentWrite(txt) {
        document.write(txt);
		//if (typeof jQuery === "undefined") {
		//	document.write(txt);
		//} else {
		//	$(document.body).append(txt);
		//}
	}	

    // generic log function, use console.log unless it isn't available
    // then revert to alert()
    function log(message) {
        if ( ! rv.debug ) {return};

        if (console && console.log) {
            console.log(message);
        } else {
            alert(message);
        }
    }

    //checks where given version string matches query
    //
    //NB: assume format is correct. Can add format check later if needed
    // from dtjava.js
    function versionCheckEx(query, version) {
        if (query == null || query.length == 0) return true;

        var c = query.charAt(query.length - 1);

        //if it is not explicit pattern but does not have update version then need to append *
        if (c != '+' && c != '*' && (query.indexOf('_') != -1 && c != '_')) {
            query = query + "*";
            c = '*';
        }

        query = query.substring(0, query.length - 1);
        //if query ends with ".", "_" then we want to strip it to allow match of "1.6.*" to shorter form such as "1.6"
        //TODO: add support for match of "1.7.0*" to "1.7"?
        if (query.length > 0) {
            var z = query.charAt(query.length - 1);
            if (z == '.' || z == '_') {
                query = query.substring(0, query.length - 1);
            }
        }
        if (c == '*') {
            //it is match if version starts from it
            return (version.indexOf(query) == 0);
        } else if (c == '+') {
            //match if query string is lexicographically smaller
            return query <= version;
        }
        return false;
    }

    function getWebStartLaunchIconURL() {
        var imageUrl = '//java.com/js/webstart.png';
        try {
            // for http/https; use protocol less url; use http for all other protocol
            return document.location.protocol.indexOf('http') != -1 ? 
                imageUrl : 'http:' + imageUrl;
        } catch (err) {
            return 'http:' + imageUrl;
        }
    }

    function arHas(ar, attr) {
        var len = ar.length;
        for (var i = 0; i < len; i++) {
            if (ar[i] === attr) return true;
        }
        return false;
    }

    function isValidAppletAttr(attr) {
        return arHas(applet_valid_attrs, attr.toLowerCase());
    }

    function isValidObjectAttr(attr) {
        return arHas(object_valid_attrs, attr.toLowerCase());
    }

    /** 
     * returns true if we can enable DT plugin auto-install without chance of
     * deadlock on cert mismatch dialog
     *
     * requestedJREVersion param is optional - if null, it will be
     * treated as installing any JRE version
     * 
     * DT plugin for 6uX only knows about JRE installer signed by SUN cert.
     * If it encounter Oracle signed JRE installer, it will have chance of
     * deadlock when running with IE.  This function is to guard against this.
     */
    function enableWithoutCertMisMatchWorkaround(requestedJREVersion) {

       // Non-IE browser are okay
       if ('MSIE' != deployJava.browserName) return true;

       // if DT plugin is 10.0.0 or above, return true
       // This is because they are aware of both SUN and Oracle signature and
       // will not show cert mismatch dialog that might cause deadlock
       if (deployJava.compareVersionToPattern(deployJava.getPlugin().version, 
                                     ["10", "0", "0"], false, true)) {
          return true;
       }

       // If we got there, DT plugin is 6uX

       if (requestedJREVersion  == null) {
          // if requestedJREVersion is not defined - it means ANY.
          // can not guarantee it is safe to install ANY version because 6uX 
          // DT does not know about Oracle certificates and may deadlock
          return false;
       }

       // 6u32 or earlier JRE installer used Sun certificate
       // 6u33+ uses Oracle's certificate
       // DT in JRE6 does not know about Oracle certificate => can only 
       // install 6u32 or earlier without risk of deadlock
       return !versionCheckEx("1.6.0_33+", requestedJREVersion);
    }

    /* HTML attribute filters */

    var rv = {

    debug: null,

    /* version of deployJava.js */
    version: "20120801",

    firefoxJavaVersion: null,

    myInterval: null,
    preInstallJREList: null,
    returnPage: null,
    brand: null,
    locale: null,
    installType: null,

    EAInstallEnabled: false,
    EarlyAccessURL: null,

    // GetJava page
    getJavaURL: 'http://jdl.sun.com/webapps/getjava/BrowserRedirect?host=java.com',

    // mime-type of the DeployToolkit plugin object
    oldMimeType: 'application/npruntime-scriptable-plugin;DeploymentToolkit',
    mimeType: 'application/java-deployment-toolkit',

    /* location of the Java Web Start launch button graphic is right next to
     * deployJava.js at: 
     *    http://java.com/js/webstart.png
     *
     * Use protocol less url here for http/https support
     */
    launchButtonPNG: getWebStartLaunchIconURL(),

    browserName: null,
    browserName2: null,

    /**
     * Returns an array of currently-installed JRE version strings.
     * Version strings are of the form #.#[.#[_#]], with the function returning
     * as much version information as it can determine, from just family
     * versions ("1.4.2", "1.5") through the full version ("1.5.0_06").
     *
     * Detection is done on a best-effort basis.  Under some circumstances
     * only the highest installed JRE version will be detected, and
     * JREs older than 1.4.2 will not always be detected.
     */
    getJREs: function() {
        var list = new Array();
        if (this.isPluginInstalled()) {
            var plugin =  this.getPlugin();
            var VMs = plugin.jvms;
            for (var i = 0; i < VMs.getLength(); i++) {
                list[i] = VMs.get(i).version;
            }
        } else {
            var browser = this.getBrowser();

            if (browser == 'MSIE') {
                if (this.testUsingActiveX('1.7.0')) {
                    list[0] = '1.7.0';
                } else if (this.testUsingActiveX('1.6.0')) {
                    list[0] = '1.6.0';
                } else if (this.testUsingActiveX('1.5.0')) {
                    list[0] = '1.5.0';
                } else if (this.testUsingActiveX('1.4.2')) {
                    list[0] = '1.4.2';
                } else if (this.testForMSVM()) {
                    list[0] = '1.1';
                }
            } else if (browser == 'Netscape Family') {
                this.getJPIVersionUsingMimeType();
                if (this.firefoxJavaVersion != null) {
                    list[0] = this.firefoxJavaVersion;
                } else if (this.testUsingMimeTypes('1.7')) {
                    list[0] = '1.7.0';
                } else if (this.testUsingMimeTypes('1.6')) {
                    list[0] = '1.6.0';
                } else if (this.testUsingMimeTypes('1.5')) {
                    list[0] = '1.5.0';
                } else if (this.testUsingMimeTypes('1.4.2')) {
                    list[0] = '1.4.2';
                } else if (this.browserName2 == 'Safari') {
                    if (this.testUsingPluginsArray('1.7.0')) {
                        list[0] = '1.7.0';
                    } else if (this.testUsingPluginsArray('1.6')) {
                        list[0] = '1.6.0';
                    } else if (this.testUsingPluginsArray('1.5')) {
                        list[0] = '1.5.0';
                    } else if (this.testUsingPluginsArray('1.4.2')) {
                        list[0] = '1.4.2';
                    }
                }
            }
        }

        if (this.debug) {
            for (var i = 0; i < list.length; ++i) {
                log('[getJREs()] We claim to have detected Java SE ' + list[i]);
            }
        }

        return list;
    },

    /**
     * Triggers a JRE installation.  The exact effect of triggering an
     * installation varies based on platform, browser, and if the
     * Deployment Toolkit plugin is installed.
     *
     * The requestVersion string is of the form #[.#[.#[_#]]][+|*],
     * which includes strings such as "1.4", "1.5.0*", and "1.6.0_02+".
     * A star (*) means "any version starting within this family" and
     * a plus (+) means "any version greater or equal to this".
     * "1.5.0*" * matches 1.5.0_06 but not 1.6.0_01, whereas
     * "1.5.0+" matches both.
     *
     * installCallback is an optional argument which holds a reference
     * to a javascript callback function for reporting install status.
     *
     * If the Deployment Toolkit plugin is not present, this will just call
     * this.installLatestJRE().
     */
    installJRE: function(requestVersion, installCallback) {
        var ret = false;
        if (this.isPluginInstalled() && 
            this.isAutoInstallEnabled(requestVersion)) {
            var installSucceeded = false;
            if (this.isCallbackSupported()) {
               installSucceeded = 
                  this.getPlugin().installJRE(requestVersion, installCallback);
            } else {
               installSucceeded = this.getPlugin().installJRE(requestVersion);
            }

            if (installSucceeded) {
                this.refresh();
                if (this.returnPage != null) {
                    document.location = this.returnPage;
                }
            }
            return installSucceeded;
        } else {
            return this.installLatestJRE();
        }
    },

    /** 
     * returns true if jre auto install for the requestedJREVersion is enabled 
     * for the local system; false otherwise
     *
     * requestedJREVersion param is optional - if not specified, it will be
     * treated as installing any JRE version
     * 
     * DT plugin for 6uX only knows about JRE installer signed by SUN cert.
     * If it encounter Oracle signed JRE installer, it will have chance of
     * deadlock when running with IE.  This function is to guard against this.
     */
    isAutoInstallEnabled: function(requestedJREVersion) {
       // if no DT plugin, return false
       if (!this.isPluginInstalled()) return false;

       if (typeof requestedJREVersion  == 'undefined') {
           requestedJREVersion = null;
       }

       return enableWithoutCertMisMatchWorkaround(requestedJREVersion);

    },

    /** 
     * returns true if jre install callback is supported
     * callback support is added since dt plugin version 10.2.0 or above
     */
    isCallbackSupported: function() {
       return this.isPluginInstalled() && 
          this.compareVersionToPattern(this.getPlugin().version, 
                                       ["10", "2", "0"], false, true);
    },

    /**
     * Triggers a JRE installation.  The exact effect of triggering an
     * installation varies based on platform, browser, and if the
     * Deployment Toolkit plugin is installed.
     *
     * In the simplest case, the browser window will be redirected to the
     * java.com JRE installation page, and (if possible) a redirect back to
     * the current URL upon successful installation.  The return redirect is
     * not always possible, as the JRE installation may require the browser to
     * be restarted.
     *
     * installCallback is an optional argument which holds a reference
     * to a javascript callback function for reporting install status.
     *
     * In the best case (when the Deployment Toolkit plugin is present), this
     * function will immediately cause a progress dialog to be displayed
     * as the JRE is downloaded and installed.
     */
    installLatestJRE: function(installCallback) {
        if (this.isPluginInstalled() && this.isAutoInstallEnabled()) {
            var installSucceeded = false;
            if (this.isCallbackSupported()) {
               installSucceeded = this.getPlugin().installLatestJRE(installCallback);
            } else {
               installSucceeded = this.getPlugin().installLatestJRE();
            }
            if (installSucceeded) {
                this.refresh();
                if (this.returnPage != null) {
                    document.location = this.returnPage;
                }
            }
            return installSucceeded;
        } else {
            var browser = this.getBrowser();
            var platform = navigator.platform.toLowerCase();
            if ((this.EAInstallEnabled == 'true') &&
                (platform.indexOf('win') != -1) &&
                (this.EarlyAccessURL != null)) {

                this.preInstallJREList = this.getJREs();
                if (this.returnPage != null) {
                    this.myInterval =
                        setInterval("deployJava.poll()", 3000);
                }

                location.href = this.EarlyAccessURL;

                // we have to return false although there may be an install
                // in progress now, when complete it may go to return page
                return false;
            } else {
                if (browser == 'MSIE') {
                    return this.IEInstall();
                } else if ((browser == 'Netscape Family') &&
                           (platform.indexOf('win32') != -1)) {
                    return this.FFInstall();
                } else {
                    location.href = this.getJavaURL +
                        ((this.returnPage != null) ?
                        ('&returnPage=' + this.returnPage) : '') +
                        ((this.locale != null) ?
                        ('&locale=' + this.locale) : '') +
                        ((this.brand != null) ?
                         ('&brand=' + this.brand) : '');
                }
                // we have to return false although there may be an install
                // in progress now, when complete it may go to return page
                return false;
            }
        }
    },


    /**
     * Ensures that an appropriate JRE is installed and then runs an applet.
     * minimumVersion is of the form #[.#[.#[_#]]], and is the minimum
     * JRE version necessary to run this applet.  minimumVersion is optional,
     * defaulting to the value "1.1" (which matches any JRE).
     * If an equal or greater JRE is detected, runApplet() will call
     * writeAppletTag(attributes, parameters) to output the applet tag,
     * otherwise it will call installJRE(minimumVersion + '+').
     *
     * After installJRE() is called, the script will attempt to detect that the
     * JRE installation has completed and begin running the applet, but there
     * are circumstances (such as when the JRE installation requires a browser
     * restart) when this cannot be fulfilled.
     *
     * As with writeAppletTag(), this function should only be called prior to
     * the web page being completely rendered.  Note that version wildcards
     * (star (*) and plus (+)) are not supported, and including them in the
     * minimumVersion will result in an error message.
     */
    runApplet: function(attributes, parameters, minimumVersion) {
        if (minimumVersion == 'undefined' || minimumVersion == null) {
            minimumVersion = '1.1';
        }

        var regex = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";

        var matchData = minimumVersion.match(regex);

        if (this.returnPage == null) {
            // if there is an install, come back here and run the applet
            this.returnPage = document.location;
        }

        if (matchData != null) {
            var browser = this.getBrowser();
            if (browser != '?') {
                if (this.versionCheck(minimumVersion + '+')) {
                    this.writeAppletTag(attributes, parameters);
                } else if (this.installJRE(minimumVersion + '+')) {
                    // after successful install we need to refresh page to pick
                    // pick up new plugin
                    this.refresh();
                    location.href = document.location;
                    this.writeAppletTag(attributes, parameters);
                }
            } else {
                // for unknown or Safari - just try to show applet
                this.writeAppletTag(attributes, parameters);
            }
        } else {
            log('[runApplet()] Invalid minimumVersion argument to runApplet():' +
                      minimumVersion);
        }
    },


    /**
     * Outputs an applet tag with the specified attributes and parameters, where
     * both attributes and parameters are associative arrays.  Each key/value
     * pair in attributes becomes an attribute of the applet tag itself, while
     * key/value pairs in parameters become <PARAM> tags.  No version checking
     * or other special behaviors are performed; the tag is simply written to
     * the page using document.writeln().
     *
     * As document.writeln() is generally only safe to use while the page is
     * being rendered, you should never call this function after the page
     * has been completed.
     */
    writeAppletTag: function(attributes, parameters) {
        var startApplet = '<' + 'applet ';
        var params = '';
        var endApplet = '<' + '/' + 'applet' + '>';
        var addCodeAttribute = true;

        if (null == parameters || typeof parameters != 'object') {
            parameters = new Object();
        }

        for (var attribute in attributes) {
            if (! isValidAppletAttr(attribute)) {
                parameters[attribute] = attributes[attribute];
            } else {
                startApplet += (' ' +attribute+ '="' +attributes[attribute] + '"');
                if (attribute == 'code') {
                    addCodeAttribute = false;
                }
            }
        }

        var codebaseParam = false;
        for (var parameter in parameters) {
            if (parameter == 'codebase_lookup') {
                codebaseParam = true;
            }
            // Originally, parameter 'object' was used for serialized
            // applets, later, to avoid confusion with object tag in IE
            // the 'java_object' was added.  Plugin supports both.
            if (parameter == 'object' || parameter == 'java_object' ||
                parameter == 'java_code' ) {
                addCodeAttribute = false;
            }
            params += '<param name="' + parameter + '" value="' +
                parameters[parameter] + '"/>';
        }
        if (!codebaseParam) {
            params += '<param name="codebase_lookup" value="false"/>';
        }

        if (addCodeAttribute) {
            startApplet += (' code="dummy"');
        }
        startApplet += '>';

        documentWrite(startApplet + '\n' + params + '\n' + endApplet);
    },
  

     /**
      * Returns true if there is a matching JRE version currently installed
      * (among those detected by getJREs()).  The versionPattern string is
      * of the form #[.#[.#[_#]]][+|*], which includes strings such as "1.4",
      * "1.5.0*", and "1.6.0_02+".
      * A star (*) means "any version within this family" and a plus (+) means
      * "any version greater or equal to the specified version".  "1.5.0*"
      * matches 1.5.0_06 but not 1.6.0_01, whereas "1.5.0+" matches both.
      *
      * If the versionPattern does not include all four version components
      * but does not end with a star or plus, it will be treated as if it
      * ended with a star.  "1.5" is exactly equivalent to "1.5*", and will
      * match any version number beginning with "1.5".
      *
      * If getJREs() is unable to detect the precise version number, a match
      * could be ambiguous.  For example if getJREs() detects "1.5", there is
      * no way to know whether the JRE matches "1.5.0_06+".  versionCheck()
      * compares only as much of the version information as could be detected,
      * so versionCheck("1.5.0_06+") would return true in in this case.
      *
      * Invalid versionPattern will result in a JavaScript error alert.
      * versionPatterns which are valid but do not match any existing JRE
      * release (e.g. "32.65+") will always return false.
      */
    versionCheck: function(versionPattern)
    {
        var index = 0;
        var regex = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?(\\*|\\+)?$";

        var matchData = versionPattern.match(regex);

        if (matchData != null) {
            // default is exact version match
            // examples:
            //    local machine has 1.7.0_04 only installed
            //    exact match request is "1.7.0_05":  return false
            //    family match request is "1.7.0*":   return true
            //    minimum match request is "1.6+":    return true
            var familyMatch = false;
            var minMatch = false;

            var patternArray = new Array();

            for (var i = 1; i < matchData.length; ++i) {
                // browser dependency here.
                // Fx sets 'undefined', IE sets '' string for unmatched groups
                if ((typeof matchData[i] == 'string') && (matchData[i] != '')) {
                    patternArray[index] = matchData[i];
                    index++;
                }
            }

            if (patternArray[patternArray.length-1] == '+') {
                // + specified in request - doing a minimum match
                minMatch = true;
                familyMatch = false;
                patternArray.length--;
            } else if (patternArray[patternArray.length-1] == '*') {
                // * specified in request - doing a family match
                minMatch = false;
                familyMatch = true;
                patternArray.length--;
            } else if (patternArray.length < 4) {
                // versionPattern does not include all four version components
                // and does not end with a star or plus, it will be treated as 
                // if it ended with a star. (family match)
                minMatch = false;
                familyMatch = true;
            }

            var list = this.getJREs();
            for (var i = 0; i < list.length; ++i) {
                if (this.compareVersionToPattern(list[i], patternArray,
                                                 familyMatch, minMatch)) {
                    return true;
                }
            }

            return false;
        } else {
            var msg = 'Invalid versionPattern passed to versionCheck: ' +
                  versionPattern;
            log('[versionCheck()] ' + msg);
            alert(msg);
            return false;
        }
    },


    /**
     * Returns true if an installation of Java Web Start of the specified
     * minimumVersion can be detected.  minimumVersion is optional, and
     * if not specified, '1.4.2' will be used.
     * (Versions earlier than 1.4.2 may not be detected.)
     */
    isWebStartInstalled: function(minimumVersion) {

        var browser = this.getBrowser();
        if (browser == '?') {
            // we really don't know - better to try to use it than reinstall
            return true;
        }

        if (minimumVersion == 'undefined' || minimumVersion == null) {
            minimumVersion = '1.4.2';
        }

        var retval = false;
        var regex = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";
        var matchData = minimumVersion.match(regex);

        if (matchData != null) {
            retval = this.versionCheck(minimumVersion + '+');
        } else {
            log('[isWebStartInstaller()] Invalid minimumVersion argument to isWebStartInstalled(): ' + minimumVersion);
            retval = this.versionCheck('1.4.2+');
        }
        return retval;
    },

    // obtain JPI version using navigator.mimeTypes array
    // if found, set the version to this.firefoxJavaVersion
    getJPIVersionUsingMimeType: function() {
        // Walk through the full list of mime types.
        for (var i = 0; i < navigator.mimeTypes.length; ++i) {
            var s = navigator.mimeTypes[i].type;
            // The jpi-version is the plug-in version.  This is the best
            // version to use.
            var m = s.match(/^application\/x-java-applet;jpi-version=(.*)$/);
            if (m != null) {
                this.firefoxJavaVersion = m[1];
                // Opera puts the latest sun JRE last not first
                if ('Opera' != this.browserName2) {
                    break;
                }
            }
        }
    },

   // launch the specified JNLP application using the passed in jnlp file
   // the jnlp file does not need to have a codebase
   // this requires JRE 7 or above to work
   // if machine has no JRE 7 or above, we will try to auto-install and then launch
   // (function will return false if JRE auto-install failed)
   launchWebStartApplication: function(jnlp) {
	var uaString = navigator.userAgent.toLowerCase();

	this.getJPIVersionUsingMimeType();

	// make sure we are JRE 7 or above
        if (this.isWebStartInstalled('1.7.0') == false) {

		// perform latest JRE auto-install
  		if ((this.installJRE('1.7.0+') == false) ||
                         ((this.isWebStartInstalled('1.7.0') == false))) {
                          return false;
                }
	}

        var jnlpDocbase = null;

        // use document.documentURI for docbase
        if (document.documentURI) {
		jnlpDocbase = document.documentURI;
	}

	// fallback to document.URL if documentURI not available
        if (jnlpDocbase == null) {
		jnlpDocbase = document.URL;
	}

        var browser = this.getBrowser();

        var launchTag;

        if (browser == 'MSIE') {

            launchTag = '<' +
                'object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" ' +
		'width="0" height="0">' +
		'<' + 'PARAM name="launchjnlp" value="' + jnlp + '"' + '>' +
	        '<' + 'PARAM name="docbase" value="' + jnlpDocbase + '"' + '>' +
                '<' + '/' + 'object' + '>';
        } else if (browser == 'Netscape Family') {

            launchTag = '<' +
		'embed type="application/x-java-applet;jpi-version=' +
		this.firefoxJavaVersion + '" ' +
                'width="0" height="0" ' +
                'launchjnlp="' +  jnlp + '"' +
                'docbase="' +  jnlpDocbase + '"' +
                ' />';
        }

        if (document.body == 'undefined' || document.body == null) {
           document.write(launchTag);
           // go back to original page, otherwise current page becomes blank
           document.location = jnlpDocbase;
        } else {
           var divTag = document.createElement("div");
           divTag.id = "div1";
           divTag.style.position = "relative";
           divTag.style.left = "-10000px";
           divTag.style.margin = "0px auto";
           divTag.className ="dynamicDiv";
           divTag.innerHTML = launchTag;
           document.body.appendChild(divTag);
        }
   },

   createWebStartLaunchButtonEx: function(jnlp, minimumVersion) {

        if (this.returnPage == null) {
            // if there is an install, come back and run the jnlp file
            this.returnPage = jnlp;
        }

        var url = 'javascript:deployJava.launchWebStartApplication(\'' + jnlp +
			'\');';

        document.write('<' + 'a href="' + url +
                       '" onMouseOver="window.status=\'\'; ' +
                       'return true;"><' + 'img ' +
                       'src="' + this.launchButtonPNG + '" ' +
                       'border="0" /><' + '/' + 'a' + '>');
    },


    /**
     * Outputs a launch button for the specified JNLP URL.  When clicked, the
     * button will ensure that an appropriate JRE is installed and then launch
     * the JNLP application.  minimumVersion is of the form #[.#[.#[_#]]], and
     * is the minimum JRE version necessary to run this JNLP application.
     * minimumVersion is optional, and if it is not specified, '1.4.2'
     * will be used.
     * If an appropriate JRE or Web Start installation is detected,
     * the JNLP application will be launched, otherwise installLatestJRE()
     * will be called.
     *
     * After installLatestJRE() is called, the script will attempt to detect
     * that the JRE installation has completed and launch the JNLP application,
     * but there are circumstances (such as when the JRE installation
     * requires a browser restart) when this cannot be fulfilled.
     */
    createWebStartLaunchButton: function(jnlp, minimumVersion) {

        if (this.returnPage == null) {
            // if there is an install, come back and run the jnlp file
            this.returnPage = jnlp;
        }

        var url = 'javascript:' +
                  'if (!deployJava.isWebStartInstalled(&quot;' +
                      minimumVersion + '&quot;)) {' +
                      'if (deployJava.installLatestJRE()) {' +
                        'if (deployJava.launch(&quot;' + jnlp + '&quot;)) {}' +
                      '}' +
                  '} else {' +
                      'if (deployJava.launch(&quot;' + jnlp + '&quot;)) {}' +
                  '}';

        document.write('<' + 'a href="' + url +
                       '" onMouseOver="window.status=\'\'; ' +
                       'return true;"><' + 'img ' +
                       'src="' + this.launchButtonPNG + '" ' +
                       'border="0" /><' + '/' + 'a' + '>');
    },


    /**
     * Launch a JNLP application, (using the plugin if available)
     */
    launch: function(jnlp) {
   /*
    * Using the plugin to launch Java Web Start is disabled for the time being
    */
        document.location=jnlp;
        return true;
    },


    /*
     * returns true if the ActiveX or XPI plugin is installed
     */
    isPluginInstalled: function() {
        var plugin = this.getPlugin();
        if (plugin && plugin.jvms) {
            return true;
        } else {
            return false;
        }
    },

    /*
     * returns true if the plugin is installed and AutoUpdate is enabled
     */
    isAutoUpdateEnabled: function() {
        if (this.isPluginInstalled()) {
            return this.getPlugin().isAutoUpdateEnabled();
        }
        return false;
    },

    /*
     * sets AutoUpdate on if plugin is installed
     */
    setAutoUpdateEnabled: function() {
        if (this.isPluginInstalled()) {
            return this.getPlugin().setAutoUpdateEnabled();
        }
        return false;
    },

    /*
     * sets the preferred install type : null, online, kernel
     */
    setInstallerType: function(type) {
        this.installType = type;
        if (this.isPluginInstalled()) {
            return this.getPlugin().setInstallerType(type);
        }
        return false;
    },

    /*
     * sets additional package list - to be used by kernel installer
     */
    setAdditionalPackages: function(packageList) {
        if (this.isPluginInstalled()) {
            return this.getPlugin().setAdditionalPackages(
                                                     packageList);
        }
        return false;
    },

    /*
     * sets preference to install Early Access versions if available
     */
    setEarlyAccess: function(enabled) {
        this.EAInstallEnabled = enabled;
    },

    /*
     * Determines if the next generation plugin (Plugin II) is default
     */
    isPlugin2: function() {
        if (this.isPluginInstalled()) {
            if (this.versionCheck('1.6.0_10+')) {
                try {
                    return this.getPlugin().isPlugin2();
                } catch (err) {
                    // older plugin w/o isPlugin2() function -
                }
            }
        }
        return false;
    },

    //support native DT plugin?
    allowPlugin: function() {
        this.getBrowser();

        // Safari and Opera browsers find the plugin but it
        // doesn't work, so until we can get it to work - don't use it.
        var ret = ('Safari' != this.browserName2 &&
            'Opera' != this.browserName2);

        return ret;
    },

    getPlugin: function() {
        this.refresh();

        var ret = null;
        if (this.allowPlugin()) {
            ret = document.getElementById('deployJavaPlugin');
        }
        return ret;
    },

    compareVersionToPattern: function(version, patternArray,
                                      familyMatch, minMatch) {
        if (version == undefined || patternArray == undefined) {
           return false;
        }
        var regex = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";
        var matchData = version.match(regex);

        if (matchData != null) {
            var index = 0;
            var result = new Array();

            for (var i = 1; i < matchData.length; ++i) {
                if ((typeof matchData[i] == 'string') && (matchData[i] != ''))
                {
                    result[index] = matchData[i];
                    index++;
                }
            }

            var l = Math.min(result.length, patternArray.length);

            // result contains what is installed in local machine
            // patternArray is what is being requested by application
            if (minMatch) {
                // minimum version match, return true if what we have (installed) 
                // is greater or equal to what is requested.  false otherwise. 
                for (var i = 0; i < l; ++i) {
                    if (result[i] < patternArray[i]) {
                        return false;
                    } else if (result[i] > patternArray[i]) {
                        return true;
                    }
                }
                return true;
            } else {
                for (var i = 0; i < l; ++i) {
                    if (result[i] != patternArray[i]) return false;
                }
                if (familyMatch) {
                    // family match - return true as long as what we have
                    // (installed) matches up to the request pattern
                    return true;
                } else { 
                    // exact match
                    // result and patternArray needs to have exact same content
                    return (result.length == patternArray.length);
                }
            }
        } else {
            return false;
        }
    },

    getBrowser: function() {

        if (this.browserName == null) {
            var browser = navigator.userAgent.toLowerCase();

            log('[getBrowser()] navigator.userAgent.toLowerCase() -> ' + browser);


            // order is important here.  Safari userAgent contains mozilla,
            // and Chrome userAgent contains both mozilla and safari.
            if ((browser.indexOf('msie') != -1) && (browser.indexOf('opera') == -1)) {
                this.browserName = 'MSIE';
                this.browserName2 = 'MSIE';
            } else if (browser.indexOf('iphone') != -1) {
                // this included both iPhone and iPad
                this.browserName = 'Netscape Family';
                this.browserName2 = 'iPhone';
            } else if ((browser.indexOf('firefox') != -1) && (browser.indexOf('opera') == -1)) {
                this.browserName = 'Netscape Family';
                this.browserName2 = 'Firefox';
            } else if (browser.indexOf('chrome') != -1) {
                this.browserName = 'Netscape Family';
                this.browserName2 = 'Chrome';
            } else if (browser.indexOf('safari') != -1) {
                this.browserName = 'Netscape Family';
                this.browserName2 = 'Safari';
            } else if ((browser.indexOf('mozilla') != -1) && (browser.indexOf('opera') == -1)) {
                this.browserName = 'Netscape Family';
                this.browserName2 = 'Other';
            } else if (browser.indexOf('opera') != -1) {
                this.browserName = 'Netscape Family';
                this.browserName2 = 'Opera';
            } else {
                this.browserName = '?';
                this.browserName2 = 'unknown';
            }

            log('[getBrowser()] Detected browser name:'+ this.browserName +
                       ', ' + this.browserName2);
        }
        return this.browserName;
    },


    testUsingActiveX: function(version) {
        var objectName = 'JavaWebStart.isInstalled.' + version + '.0';

        // we need the typeof check here for this to run on FF/Chrome
        // the check needs to be in place here - cannot even pass ActiveXObject
        // as arg to another function
        if (typeof ActiveXObject == 'undefined' || !ActiveXObject) {
            log('[testUsingActiveX()] Browser claims to be IE, but no ActiveXObject object?');
            return false;
        }

        try {
            return (new ActiveXObject(objectName) != null);
        } catch (exception) {
            return false;
        }
    },


    testForMSVM: function() {
        var clsid = '{08B0E5C0-4FCB-11CF-AAA5-00401C608500}';

        if (typeof oClientCaps != 'undefined') {
            var v = oClientCaps.getComponentVersion(clsid, "ComponentID");
            if ((v == '') || (v == '5,0,5000,0')) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    },


    testUsingMimeTypes: function(version) {
        if (!navigator.mimeTypes) {
            log ('[testUsingMimeTypes()] Browser claims to be Netscape family, but no mimeTypes[] array?');
            return false;
        }

        for (var i = 0; i < navigator.mimeTypes.length; ++i) {
            s = navigator.mimeTypes[i].type;
            var m = s.match(/^application\/x-java-applet\x3Bversion=(1\.8|1\.7|1\.6|1\.5|1\.4\.2)$/);
            if (m != null) {
                if (this.compareVersions(m[1], version)) {
                    return true;
                }
            }
        }
        return false;
    },

    testUsingPluginsArray: function(version) {
        if ((!navigator.plugins) || (!navigator.plugins.length)) {
            return false;
        }
        var platform = navigator.platform.toLowerCase();

        for (var i = 0; i < navigator.plugins.length; ++i) {
            s = navigator.plugins[i].description;
            if (s.search(/^Java Switchable Plug-in (Cocoa)/) != -1) {
                // Safari on MAC
                if (this.compareVersions("1.5.0", version)) {
                    return true;
                }
            } else if (s.search(/^Java/) != -1) {
                if (platform.indexOf('win') != -1) {
                    // still can't tell - opera, safari on windows
                    // return true for 1.5.0 and 1.6.0
                    if (this.compareVersions("1.5.0", version) ||
                        this.compareVersions("1.6.0", version)) {
                        return true;
                    }
                }
            }
        }
        // if above dosn't work on Apple or Windows, just allow 1.5.0
        if (this.compareVersions("1.5.0", version)) {
            return true;
        }
        return false;



    },

    IEInstall: function() {

        location.href = this.getJavaURL +
            ((this.returnPage != null) ?
            ('&returnPage=' + this.returnPage) : '') +
            ((this.locale != null) ?
            ('&locale=' + this.locale) : '') +
            ((this.brand != null) ? ('&brand=' + this.brand) : '');

         // should not actually get here
         return false;
    },

    done: function (name, result) {
    },

    FFInstall: function() {

        location.href = this.getJavaURL +
            ((this.returnPage != null) ?
            ('&returnPage=' + this.returnPage) : '') +
            ((this.locale != null) ?
            ('&locale=' + this.locale) : '') +
            ((this.brand != null) ? ('&brand=' + this.brand) : '') +
            ((this.installType != null) ?
                ('&type=' + this.installType) : '');

         // should not actually get here
         return false;
    },

    // return true if 'installed' (considered as a JRE version string) is
    // greater than or equal to 'required' (again, a JRE version string).
    compareVersions: function(installed, required) {

        var a = installed.split('.');
        var b = required.split('.');

        for (var i = 0; i < a.length; ++i) {
            a[i] = Number(a[i]);
        }
        for (var i = 0; i < b.length; ++i) {
            b[i] = Number(b[i]);
        }
        if (a.length == 2) {
            a[2] = 0;
        }

        if (a[0] > b[0]) return true;
        if (a[0] < b[0]) return false;

        if (a[1] > b[1]) return true;
        if (a[1] < b[1]) return false;

        if (a[2] > b[2]) return true;
        if (a[2] < b[2]) return false;

        return true;
    },

    enableAlerts: function() {
        // reset this so we can show the browser detection
        this.browserName = null;
        this.debug = true;
    },

    poll: function() {

        this.refresh();
        var postInstallJREList = this.getJREs();

        if ((this.preInstallJREList.length == 0) &&
            (postInstallJREList.length != 0)) {
            clearInterval(this.myInterval);
            if (this.returnPage != null) {
                location.href = this.returnPage;
            };
        }

        if ((this.preInstallJREList.length != 0) &&
            (postInstallJREList.length != 0) &&
            (this.preInstallJREList[0] != postInstallJREList[0])) {
            clearInterval(this.myInterval);
            if (this.returnPage != null) {
                location.href = this.returnPage;
            }
        }

    },

    writePluginTag: function() {
        var browser = this.getBrowser();

        if (browser == 'MSIE') {
            document.write('<' +
                'object classid="clsid:CAFEEFAC-DEC7-0000-0001-ABCDEFFEDCBA" ' +
                'id="deployJavaPlugin" width="0" height="0">' +
                '<' + '/' + 'object' + '>');
        } else if (browser == 'Netscape Family' && this.allowPlugin()) {
            this.writeEmbedTag();
        }
    },

    refresh: function() {
        navigator.plugins.refresh(false);

        var browser = this.getBrowser();
        if (browser == 'Netscape Family' && this.allowPlugin()) {
            var plugin = document.getElementById('deployJavaPlugin');
            // only do this again if no plugin
            if (plugin == null) {
                this.writeEmbedTag();
            }
        }
     },

    writeEmbedTag: function() {
        var written = false;
        if (navigator.mimeTypes != null) {
            for (var i=0; i < navigator.mimeTypes.length; i++) {
                if (navigator.mimeTypes[i].type == this.mimeType) {
                    if (navigator.mimeTypes[i].enabledPlugin) {
                        document.write('<' +
                            'embed id="deployJavaPlugin" type="' +
                            this.mimeType + '" hidden="true" />');
                        written = true;
                    }
                }
            }
            // if we ddn't find new mimeType, look for old mimeType
            if (!written) for (var i=0; i < navigator.mimeTypes.length; i++) {
                if (navigator.mimeTypes[i].type == this.oldMimeType) {
                    if (navigator.mimeTypes[i].enabledPlugin) {
                        document.write('<' +
                            'embed id="deployJavaPlugin" type="' +
                            this.oldMimeType + '" hidden="true" />');
                    }
                }
            }
        }
    }
    }; // deployJava object

    rv.writePluginTag();
    if (rv.locale == null) {
        var loc = null;

        if (loc == null) try {
            loc = navigator.userLanguage;
        } catch (err) { }

        if (loc == null) try {
            loc = navigator.systemLanguage;
        } catch (err) { }

        if (loc == null) try {
            loc = navigator.language;
        } catch (err) { }

        if (loc != null) {
            loc.replace("-","_")
            rv.locale = loc;
        }
    }

    return rv;
}();

// ------------------------------ icpBravoApplet helper ------------------------------

var icpBravoApplet = new function () {

    this.appletLoaded = false;
    this.jQueryAvailable = (typeof jQuery != "undefined");
    this.jQueryUIAvailable = this.jQueryAvailable && (typeof jQuery.ui != "undefined");
    this.blockUIAvailable = this.jQueryAvailable && (typeof jQuery.blockUI != "undefined");
    this.self = this;
    //this.onAppletException = null;
	
    this.getApplet = function () {

        if (!this.appletLoaded)
            throw "O applet ainda não foi carregado";

        return document.getElementById('ICPBravoApplet');
    };
	
	this.checkResult = function (checkResult) {
		if (checkResult === null || checkResult === false) {
			var errors = icpBravoApplet.getApplet().getErrors();
			if (errors == null || errors == "") {
				errors = "Unknown error occurred in applet";
			}
			if (this.onAppletException) {
				if (!(this.onAppletException(errors) === false)) {
					throw errors;
				}
			} else {
				throw errors;
			}
		}
	};

    this.downloadJava = function () {
    	//deployJava.installLatestJRE();
    	document.location.href = 'http://www.java.com/getjava/';
    };

	this.testJava = function(onFailed) {

		if (deployJava.versionCheck("1.6") || deployJava.versionCheck("1.7"))
			return true;

		onFailed('O seu navegador não suporta a versão 1.6 ou superior do Java!\n\nVocê será redirecionado para a página de instalação do Java.\n\nPor favor, instale o java e tente novamente.', icpBravoApplet.downloadJava);
	};

    this.start = function (args) {

		if (!args.appletUrl)
			throw "Especifique a url do applet";
	
		if (!args.timeout)
			args.timeout = 30000;
			
		if (!args.onLoadFailed)
			args.onLoadFailed = this.onLoadFailedDefault
			
		if (args.onAppletException)
			this.onAppletException = args.onAppletException;

        if (this.appletLoaded) {

            if (args.onLoad) {
                setTimeout(function () { args.onLoad(); }, 500); // para contemplar o caso do programador assumir que o onAppletLoaded é executado assincronamente depois do retorno da função start
            }

        } else {

            if (!this.testJava(args.onLoadFailed))
                return; 				
				
			var attributes = {
				id: 'ICPBravoApplet',
				code: 'br.com.oaks.ICPBravo.appletMini.AppletICPBravoSignBytes',
				archive: args.appletUrl,
				width: 0,
				height: 0
			};
			deployJava.writeAppletTag(attributes, null);
			
			if (args.message != null && icpBravoApplet.blockUIAvailable) {
				icpBravoApplet.blockUI(args.message, function() {
					icpBravoApplet.checkApplet(args.onLoad, args.onLoadFailed, args.timeout/1000);
				});
			} else {
				icpBravoApplet.checkApplet(args.onLoad, args.onLoadFailed, args.timeout/1000);
			}
        }
    };

    this.checkApplet = function (onLoad, onLoadFailed, timeoutCount) {
        this.log("Checking applet ...");
        try {
            if (document.getElementById('ICPBravoApplet').isRunning()) {
                this.log("Applet loaded!");
                this.appletLoaded = true;
            }else{
            	this.log("isRunning false");
            }
        } catch (e) {
        }
        if (this.appletLoaded) {
			var vmArc = this.getApplet().getVMArc();
			this.checkResult(vmArc);
			if (vmArc != 32) {
				onLoadFailed('A versão 64 bits do Java não é suportada. Por favor, instale a versão 32 bits e tente novamente.', function () { });
			} else {
				if (onLoad) {
					onLoad();
				}
			}
        } else if (timeoutCount > 0) {
            setTimeout(function () { icpBravoApplet.checkApplet(onLoad, onLoadFailed, timeoutCount-1) }, 1000);
        } else {
			onLoadFailed('O applet de acesso ao dispositivo criptográfico não pôde ser carregado.', function () { });
		}
    };

	this.log = function(msg) {
		if (window.console) {
			console.log(msg);
		}
	};
	
	this.onLoadFailedDefault = function (message, suggestedAction) {
		icpBravoApplet.showMessage(message, 'Erro ao carregar o applet', suggestedAction);
	};
	
	
	// ----- applet calls -----------------------------------------------------------
	
	this.loadCertificatesAsync = function(managerName, selectId, onSuccess, onError, msg){
		 if (!managerName) {
            managerName = "Windows-MY";
        }
		
		var result = this.getApplet().beginLoadCertificates(managerName);
		this.checkResult(result);
		
		this.beginPollAsyncOperation(function (applet) {
			return applet.endLoadCertificates();
		}, function (certs) {
	        if (selectId) {
	            if (this.jQueryAvailable) {
	                var e = $("#" + selectId);
	                e.find('option').remove();
	                $.each(certs, function (alias, name) {
	                    e.append("<option value='" + alias + "'>" + name + "</option>");
	                });
	            } else {
	                var e = document.getElementById(selectId);
	                e.options.length = 0;
	                for (i = 0; i < certs.length; i++) {
	                    e.options[e.options.length] = new Option(certs[i], i);
	                }
	            }
	        }
	        if (onSuccess) {
	        	onSuccess(certs);
	        }
		}, onError, msg);
	}
	
    this.loadCertificates = function (managerName, selectId) {
        if (!managerName) {
            managerName = "Windows-MY";
        }
        var certs = this.getApplet().loadCertificates(managerName);
		this.checkResult(certs);
        if (selectId) {
            if (this.jQueryAvailable) {
                var e = $("#" + selectId);
                e.find('option').remove();
                $.each(certs, function (alias, name) {
                    e.append("<option value='" + alias + "'>" + name + "</option>");
                });
            } else {
                var e = document.getElementById(selectId);
                e.options.length = 0;
                for (i = 0; i < certs.length; i++) {
                    e.options[e.options.length] = new Option(certs[i], i);
                }
            }
        }
        return certs;
    };

    this.setFilterCPF = function (cpf) {
        var result = this.getApplet().setFilterCPF(cpf);
		this.checkResult(result);
    };

    this.setFilterICPBrasil = function (value) {
        var result = this.getApplet().setFilterICPBrasil(value);
		this.checkResult(result);
    };

    this.getCertificatePropertiesAsync = function (certIndex, onSuccess, onError, msg) {
		var result = this.getApplet().beginGetCertificateProperties(certIndex);
		this.checkResult(result);		
		this.beginPollAsyncOperation(function (applet) {
			return applet.endGetCertificateProperties();
		}, onSuccess, onError, msg);
	}
    
    this.getCertificateProperties = function (certIndex) {
        var result = this.getApplet().getCertificateProperties(certIndex);
		this.checkResult(result);
		return result;
    };
    
    this.signSimpleBytesAsync = function (certIndex, toSign,  onSuccess, onError, msg) {
    	icpBravoApplet.log("certIndex: "+certIndex);
    	icpBravoApplet.log("toSign: "+toSign);
    	var result = this.getApplet().beginSignSimpleBytes(certIndex, toSign);
    	icpBravoApplet.log("result: "+result);
    	this.checkResult(result);
		this.beginPollAsyncOperation(function (applet) {
			return applet.endSignSimpleBytes();
		}, onSuccess, onError, msg);
	}

    this.signSimpleBytes = function (certIndex, toSign) {
    	icpBravoApplet.log("certIndex: "+certIndex);
    	icpBravoApplet.log("toSign: "+toSign);
    	var result = this.getApplet().signSimpleBytes(certIndex, toSign);
    	icpBravoApplet.log("result: "+result);
    	this.checkResult(result);
		return result;
    };
	
	
	// ----- async operation polling -------------------------------------------------------------

	this.beginPollAsyncOperation = function (endAction, onSuccess, onError, msg) {
		var blockUI = (msg != null && icpBravoApplet.blockUIAvailable);
		var args = { endAction: endAction, onSuccess: onSuccess, onError: onError, unblockUI: blockUI };
		if (blockUI) {
			icpBravoApplet.blockUI(msg, icpBravoApplet.pollAsyncOperation, args);
		} else {
			setTimeout(icpBravoApplet.pollAsyncOperation, 500, args);
		}
	}

	this.pollAsyncOperation = function (args) {		
		if (icpBravoApplet.getApplet().getAsyncOperationDone()) {
			if (args.unblockUI) {
				icpBravoApplet.unblockUI(icpBravoApplet.endPollAsyncOperation, args);
			} else {
				icpBravoApplet.endPollAsyncOperation(args);
			}
		} else {
			setTimeout(icpBravoApplet.pollAsyncOperation, 500, args);
		}		
	}

	this.endPollAsyncOperation = function (args) {
		try {
			var result = args.endAction(icpBravoApplet.getApplet());
			icpBravoApplet.checkResult(result);
			if (args.onSuccess) {
				args.onSuccess(result);
			}
		} catch (err) {
			if (args.onError) {				
				args.onError(err);
			}
		}
	}

	this.blockUI = function (msg, onBlockFunc, onBlockArg) {
		$.blockUI({
			css: {
				border: 'none',
				padding: '15px',
				backgroundColor: '#000',
				'-webkit-border-radius': '10px',
				'-moz-border-radius': '10px',
				opacity: .5,
				color: '#fff'
			},
			message: msg,
			onBlock: function () {
				if (onBlockFunc) {
					onBlockFunc(onBlockArg);
				}
			}
		});
	}

	this.unblockUI = function (onUnblockFunc, onUnblockFuncArg) {
		$.unblockUI({
			onUnblock: function () {
				if (onUnblockFunc) {
					onUnblockFunc(onUnblockFuncArg);
				}
			}
		});
	}

	this.getErrors = function () {
		return this.getApplet().getErrors();
	};

	this.showMessage = function(message, title, onClose) {
		if (this.jQueryUIAvailable) {
			this.showMessageUI({
				message: message.replace('\n', '<br/>'),
				title: title,
				width: 470,
				style: 'text-align:center;font-size:1.2em',
				onClose: onClose
			});
		} else {
			alert(message);
			if (onClose) {
				onClose();
			}
		}
	}
	
	this.showMessageUI = function (p, t) {
		var msg, title, style = "font-size: 1.2em;";
		var center = false;
		if (typeof p == "string") {
			msg = p;
			title = t;
			center = true;
		} else {
			msg = p.message;
			title = p.title;
			if (p.center != null) {
				center = p.center;
			}
			if (p.style != null) {
				style = p.style;
			}
		}
		if (!title)
			title = "";
		if (center) {
			style += "vertical-align: middle;text-align:center; line-height: 90px;";
		}
		var e = $("<div title='" + title + "' style='" + style + "' >" + msg + "</div>");
		var dialogOptions = {
			modal: true,
			buttons: [
				{
					text: 'OK',
					click: function () {
						$(this).dialog('close');
					}
				}
			],
			close: function (event, ui) {
				if (p.onClose) {
					p.onClose();
				}
			}
		};
		if (p.width != null) {
			dialogOptions["width"] = p.width;
		}
		e.dialog(dialogOptions);
	};

    // Funções para prover funcionalidade de Ajax. Argumentos suportados: type, url, data, success, error

    this.ajax = function (args) {

        if (this.jQueryAvailable) {
            args.cache = false;
            $.ajax(args);
            return;
        }

        if (!args.type)
            args.type = "GET";

        var httpRequest;
 
        if (window.XMLHttpRequest) { // Mozilla, Safari, ...
            httpRequest = new XMLHttpRequest();
        } else if (window.ActiveXObject) { // IE
            try {
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            } 
            catch (e) {
                try {
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } 
                catch (e) {}
            }
        }
 
        if (!httpRequest) {
            throw "Não foi possível realizar chamada Ajax";
        }

        httpRequest.onreadystatechange = function () { icpBravoApplet.ajaxCallback(httpRequest, args); };
        httpRequest.open(args.type, args.url, true);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var dataText = "_=" + new Date().getTime(); // para evitar cache
        if (args.data) {
            for (var key in args.data) {
                dataText += "&" + key + '=' + encodeURIComponent(args.data[key]);
            }
        }
        httpRequest.send(dataText);
    };

    this.ajaxCallback = function (httpRequest, args) {
        if (httpRequest.readyState === 4) {
            if (httpRequest.status === 200) {
                if (args.success) {
                    var response = json_parse(httpRequest.responseText);
                    args.success(response);
                }
            } else {
                if (args.error) {
                    args.error();
                }
            }
        }
    }
}

// deprecated - função provida apenas para "backward compatibility"
function ICPBravoApplet() {
    return icpBravoApplet.getApplet();
}

