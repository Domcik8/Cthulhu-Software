/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Calendar config by Karolis
    function calendarOnlyMonday(date) {
        var day = date.getDay();
        return [(day == 1), '']
    }
    
    function calendarOnlySunday(date) {
        var day = date.getDay();
        return [(day == 0), '']
    }
    
    PrimeFaces.locales ['lt'] = { 
        closeText: 'Uždaryti',
        prevText: 'Atgal', 
        nextText: 'Pirmyn', 
        monthNames: ['Sausis', 'Vasaris', 'Kovas', 'Balandis', 'Gegužė', 'Birželis', 'Liepa', 'Rugpjūtis', 'Rugsėjis', 'Spalis', 'Lapkritis', 'Gruodis' ],
        monthNamesShort: ['Sau', 'Vas', 'Kov', 'Bal', 'Geg', 'Bir', 'Lie', 'Rgp', 'Rgs', 'Spl', 'Lap', 'Grd' ], 
        dayNames: ['Sekmadienis', 'Pirmadienis', 'Antradienis', 'Trečiadienis', 'Ketvirtadienis', 'Penktadienis', 'Šeštadienis'], 
        dayNamesShort: ['S', 'P', 'A', 'T', 'K', 'Pn', 'Š'], 
        dayNamesMin: ['S', 'P', 'A', 'T', 'K', 'Pn', 'Š'], 
        weekHeader: 'Savaitė', firstDay: 1, isRTL: false, showMonthAfterYear: false, 
        yearSuffix:'', timeOnlyTitle: 'Tik laikas', timeText: 'Laikas', hourText: 'Valandos', 
        minuteText: 'Minutės', secondText: 'Sekundės', currentText: 'Dabartinė data',
        ampm: false, month: 'Mėnesis', week: 'Savaitė', day: 'Diena', allDayText: 'Visa diena', 
        messages: {
            'javax.faces.component.UIInput.REQUIRED': '{0}: Validation Error: Value is required.', 
            'javax.faces.converter.IntegerConverter.INTEGER': '{2}: \'{0}\' must be a number consisting of one or more digits.', 
            'javax.faces.converter.IntegerConverter.INTEGER_detail': '{2}: \'{0}\' must be a number between -2147483648 and 2147483647 Example: {1}', 
            'javax.faces.converter.DoubleConverter.DOUBLE': '{2}: \'{0}\' must be a number consisting of one or more digits.', 
            'javax.faces.converter.DoubleConverter.DOUBLE_detail': '{2}: \'{0}\' must be a number between 4.9E-324 and 1.7976931348623157E308 Example: {1}',
            'javax.faces.converter.BigDecimalConverter.DECIMAL': '{2}: \'{0}\' must be a signed decimal number.', 
            'javax.faces.converter.BigDecimalConverter.DECIMAL_detail': '{2}: \'{0}\' must be a signed decimal number consisting of zero or more digits, that may be followed by a decimal point and fraction. Example: {1}',
            'javax.faces.converter.BigIntegerConverter.BIGINTEGER': '{2}: \'{0}\' must be a number consisting of one or more digits.', 
            'javax.faces.converter.BigIntegerConverter.BIGINTEGER_detail': '{2}: \'{0}\' must be a number consisting of one or more digits. Example: {1}',
            'javax.faces.converter.ByteConverter.BYTE': '{2}: \'{0}\' must be a number between 0 and 255.', 
            'javax.faces.converter.ByteConverter.BYTE_detail': '{2}: \'{0}\' must be a number between 0 and 255. Example: {1}', 
            'javax.faces.converter.CharacterConverter.CHARACTER': '{1}: \'{0}\' must be a valid character.', 
            'javax.faces.converter.CharacterConverter.CHARACTER_detail': '{1}: \'{0}\' must be a valid ASCII character.', 
            'javax.faces.converter.ShortConverter.SHORT': '{2}: \'{0}\' must be a number consisting of one or more digits.', 
            'javax.faces.converter.ShortConverter.SHORT_detail': '{2}: \'{0}\' must be a number between -32768 and 32767 Example: {1}', 
            'javax.faces.converter.BooleanConverter.BOOLEAN': '{1}: \'{0}\' must be \'true\' or \'false\'', 
            'javax.faces.converter.BooleanConverter.BOOLEAN_detail': '{1}: \'{0}\' must be \'true\' or \'false\'. Any value other than \'true\' will evaluate to \'false\'.', 
            'javax.faces.validator.LongRangeValidator.MAXIMUM': '{1}: Validation Error: Value is greater than allowable maximum of \'{0}\'', 
            'javax.faces.validator.LongRangeValidator.MINIMUM': '{1}: Validation Error: Value is less than allowable minimum of \'{0}\'', 
            'javax.faces.validator.LongRangeValidator.NOT_IN_RANGE': '{2}: Validation Error: Specified attribute is not between the expected values of {0} and {1}.', 
            'javax.faces.validator.LongRangeValidator.TYPE={0}': 'Validation Error: Value is not of the correct type.', 
            'javax.faces.validator.DoubleRangeValidator.MAXIMUM': '{1}: Validation Error: Value is greater than allowable maximum of \'{0}\'', 
            'javax.faces.validator.DoubleRangeValidator.MINIMUM': '{1}: Validation Error: Value is less than allowable minimum of \'{0}\'', 
            'javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE': '{2}: Validation Error: Specified attribute is not between the expected values of {0} and {1}', 
            'javax.faces.validator.DoubleRangeValidator.TYPE={0}': 'Validation Error: Value is not of the correct type', 
            'javax.faces.converter.FloatConverter.FLOAT': '{2}: \'{0}\' must be a number consisting of one or more digits.', 
            'javax.faces.converter.FloatConverter.FLOAT_detail': '{2}: \'{0}\' must be a number between 1.4E-45 and 3.4028235E38 Example: {1}', 
            'javax.faces.converter.DateTimeConverter.DATE': '{2}: \'{0}\' could not be understood as a date.', 
            'javax.faces.converter.DateTimeConverter.DATE_detail': '{2}: \'{0}\' could not be understood as a date. Example: {1}', 
            'javax.faces.converter.DateTimeConverter.TIME': '{2}: \'{0}\' could not be understood as a time.', 
            'javax.faces.converter.DateTimeConverter.TIME_detail': '{2}: \'{0}\' could not be understood as a time. Example: {1}', 
            'javax.faces.converter.DateTimeConverter.DATETIME': '{2}: \'{0}\' could not be understood as a date and time.', 
            'javax.faces.converter.DateTimeConverter.DATETIME_detail': '{2}: \'{0}\' could not be understood as a date and time. Example: {1}', 
            'javax.faces.converter.DateTimeConverter.PATTERN_TYPE': '{1}: A \'pattern\' or \'type\' attribute must be specified to convert the value \'{0}\'', 
            'javax.faces.converter.NumberConverter.CURRENCY': '{2}: \'{0}\' could not be understood as a currency value.', 
            'javax.faces.converter.NumberConverter.CURRENCY_detail': '{2}: \'{0}\' could not be understood as a currency value. Example: {1}', 
            'javax.faces.converter.NumberConverter.PERCENT': '{2}: \'{0}\' could not be understood as a percentage.', 
            'javax.faces.converter.NumberConverter.PERCENT_detail': '{2}: \'{0}\' could not be understood as a percentage. Example: {1}', 
            'javax.faces.converter.NumberConverter.NUMBER': '{2}: \'{0}\' could not be understood as a date.', 
            'javax.faces.converter.NumberConverter.NUMBER_detail': '{2}: \'{0}\' is not a number. Example: {1}', 
            'javax.faces.converter.NumberConverter.PATTERN': '{2}: \'{0}\' is not a number pattern.', 
            'javax.faces.converter.NumberConverter.PATTERN_detail': '{2}: \'{0}\' is not a number pattern. Example: {1}', 
            'javax.faces.validator.LengthValidator.MINIMUM': '{1}: Validation Error: Length is less than allowable minimum of \'{0}\'', 
            'javax.faces.validator.LengthValidator.MAXIMUM': '{1}: Validation Error: Length is greater than allowable maximum of \'{0}\'', 
            'javax.faces.validator.RegexValidator.PATTERN_NOT_SET': 'Regex pattern must be set.', 
            'javax.faces.validator.RegexValidator.PATTERN_NOT_SET_detail': 'Regex pattern must be set to non-empty value.', 
            'javax.faces.validator.RegexValidator.NOT_MATCHED': 'Regex Pattern not matched', 
            'javax.faces.validator.RegexValidator.NOT_MATCHED_detail': 'Regex pattern of \'{0}\' not matched',
            'javax.faces.validator.RegexValidator.MATCH_EXCEPTION': 'Error in regular expression.', 
            'javax.faces.validator.RegexValidator.MATCH_EXCEPTION_detail': 'Error in regular expression, \'{0}\'' } }; 

//Dialog config
    function disableScrolling(){
        $("body").css({ overflow: 'hidden' })
    }

    function enableScrolling(){
         $("body").css({ overflow: 'inherit' })
    }
//var elem = document.getElementById('fb-button');
//elem.onclick = displayDate();

$(document).ready(function() {
                FB.init({
                  appId      : '198659840500311',
                  cookie     : true,    // enable cookies to allow the server to access 
                                        // the session
                  xfbml      : true,
                  version    : 'v2.5'
                });
              });

function testAPI() {
  console.log('Welcome!  Fetching your information.... ');
  FB.api('/me', function(response) {
    console.log('Successful login for: ' + response.name);
    document.getElementById('status').innerHTML =
      'Thanks for logging in, ' + response.name + '!';
  });
}

function displayUser(user) {

        alert(user.name);
}

function getUserInfo() {
    FB.getLoginStatus(function(response) {
       FB.api('/me', function(response) {
 
          var str="<b>Name</b> : "+response.name+"<br>";
          str +="<b>Link: </b>"+response.link+"<br>";
          str +="<b>Username:</b> "+response.username+"<br>";
          str +="<b>id: </b>"+response.id+"<br>";
          str +="<b>Email:</b> "+response.email+"<br>";
          
          document.getElementById("status").innerHTML=str;
        });
    });     
}



document.getElementById("changeTextButton").onclick=changeText;

function changeText()
{
    document.getElementById("test").innerHTML = "text changed";
}

function loginFb() {
    
    FB.login(function(response) {
       location.reload();
       window.load = testAPI(); 
    });
  
}

function logoutFb() {

    FB.getLoginStatus(function(response) {
        if (response && response.status === 'connected') {
           FB.logout(function(response) {
               location.reload();
           }); 
        }
    });
}

// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
  console.log('statusChangeCallback');
  console.log(response);
  // The response object is returned with a status field that lets the
  // app know the current login status of the person.
  // Full docs on the response object can be found in the documentation
  // for FB.getLoginStatus().
  if (response.status === 'connected') {
    // Logged into your app and Facebook.
    testAPI();
  } else if (response.status === 'not_authorized') {
    // The person is logged into Facebook, but not your app.
    document.getElementById('status').innerHTML = 'Please log ' +
      'into this app.';
  } else {
    // The person is not logged into Facebook, so we're not sure if
    // they are logged into this app or not.
    document.getElementById('status').innerHTML = 'Please log ' +
      'into Facebook.';
  }
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}
/*
    window.fbAsyncInit = function() {
    FB.init({
      appId      : '198659840500311',
      cookie     : true,  // enable cookies to allow the server to access 
                          // the session
      xfbml      : true,  // parse social plugins on this page
      version    : 'v2.5' // use graph api version 2.5
    });

    // Now that we've initialized the JavaScript SDK, we call 
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.

    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });

    };*/
/*
// Load the SDK asynchronously
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
*/
// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.

