/*
Implementation for commonly required functions.
Created by    on 6 January 2021
Dependencies : JQuery, SweetAlert - js
 */

const premitiveTypes = 'string number boolean undefined';

const shortMonthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
const fullMonthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

const bdIntegerFormatter = Intl.NumberFormat('en-IN', {maximumFractionDigits: 0});
const bdCurrencyFormatter = Intl.NumberFormat('en-IN', {maximumFractionDigits: 2, minimumFractionDigits: 2});
const usCurrencyFormatter = Intl.NumberFormat('en-US', {maximumFractionDigits: 2, minimumFractionDigits: 2});

function formatBdIntegers(value) {
    if (isNumber(value)) {
        return bdIntegerFormatter.format(value);
    }
    return value;
}

function formatBdtInEnglish(value) {
    if (isNumber(value)) {
        return bdCurrencyFormatter.format(value);
    }
    return value;
}

function formatUsCurrency(value) {
    if (isNumber(value)) {
        return usCurrencyFormatter.format(value);
    }
    return value;
}

function clearFormFieldValuesFocused(elementId) {
    $("#" + elementId).find(':input').each(function () {
        switch (this.type) {
            case 'password':
            case 'text':
            case 'textarea':
            case 'file':
            case 'select-one':
            case 'select-multiple':
            case 'date':
            case 'number':
            case 'tel':
            case 'email':
                $(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
                break;
        }

    });
    clearDropdownListsByElementId(elementId);
}

function clearFormFieldValuesGeneralized(elementId) {
    $("#" + elementId).find(':input').val('');
    clearDropdownListsByElementId(elementId);
}

function clearDropdownListsByElementId(elementId) {
    // $("#" + elementId).find(':select').val('');
    let element = document.getElementById(elementId);
    let dropdowns = element.getElementsByTagName('select');
    for (menu of dropdowns) {
        menu.value = '';
    }
}

function isNumber(value) {
    return typeof value !== 'undefined' && (typeof value === 'number' || (typeof value === 'string' && !isNaN(Number(value))));
}

function formatDate_DD_MMM_YYYY(dateString_yyyy_mm_dd, delimeter = ".") {
    //console.log("ptp date : "+dateString_yyyy_mm_dd)
    if (!isValidDate(dateString_yyyy_mm_dd)) return dateString_yyyy_mm_dd;
    let date = new Date(dateString_yyyy_mm_dd);
    return endureTwoDigitDay(date.getDate()) + delimeter + shortMonthNames[date.getMonth()] + delimeter + date.getFullYear();
}

function formatDate_MMMM_YYYY(dateString_yyyy_mm_dd, delimeter = ", ") {
    if (!isValidDate(dateString_yyyy_mm_dd)) return dateString_yyyy_mm_dd;
    let date = new Date(dateString_yyyy_mm_dd);
    return fullMonthNames[date.getMonth()] + delimeter + date.getFullYear();
}

function formatDate_MMMM_YYYY(dateString_yyyy_mm_dd, delimeter = ", ", monthModifier = 0) {
    if (!isValidDate(dateString_yyyy_mm_dd)) return dateString_yyyy_mm_dd;
    let date = new Date(dateString_yyyy_mm_dd);
    if (typeof monthModifier === 'number')
        date.setMonth(date.getMonth() + monthModifier);
    return fullMonthNames[date.getMonth()] + delimeter + date.getFullYear();
}

function formatDate_DD_MM_YYYY(dateString_yyyy_mm_dd, delimeter = ".") {
    if (!isValidDate(dateString_yyyy_mm_dd)) return dateString_yyyy_mm_dd;
    let date = new Date(dateString_yyyy_mm_dd);
    return endureTwoDigitDay(date.getDate()) + delimeter + endureTwoDigitDay(date.getMonth() + 1) + delimeter + date.getFullYear();
}
function formatDate_DD_MM_YYYY_delimeter_hipehen(dateString_yyyy_mm_dd, delimeter = "-") {
    if (!isValidDate(dateString_yyyy_mm_dd)) return dateString_yyyy_mm_dd;
    let date = new Date(dateString_yyyy_mm_dd);
    return endureTwoDigitDay(date.getDate()) + delimeter + endureTwoDigitDay(date.getMonth() + 1) + delimeter + date.getFullYear();
}

function endureTwoDigitDay(value) {
    if (!value) return '';
    value = value.toString();
    if (value.length < 2)
        value = '0' + value;
    else if (value.length > 2)
        value = value.substr(0, 2);
    return value;
}

function isValidDate(dateString_yyyy_mm_dd) {
    if (typeof dateString_yyyy_mm_dd === 'string') {
        return dateString_yyyy_mm_dd.match(/^\d{4}-([01]?[0-9])-([0-2]?[0-9])$/) ||
            dateString_yyyy_mm_dd.match(/(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+)|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d)|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d)/) ||
            dateString_yyyy_mm_dd.match(/(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+([+-][0-2]\d:[0-5]\d|Z))|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d([+-][0-2]\d:[0-5]\d|Z))|(\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d([+-][0-2]\d:[0-5]\d|Z))/);
    } else if (dateString_yyyy_mm_dd && typeof dateString_yyyy_mm_dd.getMonth === 'function') {
        return true;
    }
    return false;
}

function isEqualIgnoreCase(source, target) {
    if (typeof source !== 'string' || typeof target !== 'string') return false;
    source = source.toLowerCase().replace(/\s/g, '');
    target = target.toLowerCase().replace(/\s/g, '');
    return source === target;
}

function isEqual(source, target) {
    let sourceType = typeof source;
    if (!isOfSimilarType(source, target)) return false;
    if (sourceType === 'string') return isEqualIgnoreCase(source, target);
    if (premitiveTypes.includes(sourceType)) return source === target;
    return false;
}

function isOfSimilarType(source, target) {
    return typeof source === typeof target;
}

function getStringValueOrDash(value) {
    if (!value || value === 'null') return '-';
    else if (premitiveTypes.includes(typeof value)) return value + '';
    else return JSON.stringify(value);
}

function filterObjectOptions(masterArray, filterableFieldName, fieldValue) {
    let validFilterRequest = Array.isArray(masterArray) &&
        typeof filterableFieldName === 'string' && fieldValue &&
        masterArray.length > 0; // && masterArray[0][filterableField]

    if (validFilterRequest) {
        let isArrayField = Array.isArray(fieldValue);
        let isPremitiveField = premitiveTypes.includes(typeof fieldValue[0]);
        fieldValue = isArrayField && !isPremitiveField ? fieldValue.map(item => JSON.stringify(item)) : fieldValue;
        return masterArray.filter(element => {
            if (isArrayField) {
                if (isPremitiveField)
                    return fieldValue.includes(element[filterableFieldName]);
                else
                    return fieldValue.includes(JSON.stringify(element[filterableFieldName]));
            }
            else
                return Object.is(element[filterableFieldName], fieldValue);
        });
    }
    return [];
}

function notifyError() {
    swal("Something went wrong");
}

function notifyError(data) {
    if (typeof data === 'string' && data.includes('login'))
        window.location.reload();
    else
        swal("Something went wrong");
}

function getNewDataIgnoringPreviousData(newData, previousData) {
    if (previousData !== newData) {
        previousData = newData;
        return newData;
    } else {
        return '';
    }
}

function export2Word(elementId, filename = '') {
    let element = document.getElementById(elementId);
    if (!element) {
        swal('No element found');
        return;
    }
    let cssText = getAllDomCssRulesAsText();
    let printScript =
        '<script>' +
        'function removeElementsFromDocument(elementId) {' +
        '    let elements = document.getElementsByTagName(elementId);' +
        '    while (elements.length) elements[0].remove();' +
        '}' +
        'removeElementsFromDocument("button");' +
        'removeElementsFromDocument("label");' +
        'removeElementsFromDocument("input");' +
        '</script>';

    var html = "<html xmlns:o='urn:schemas-microsoft-com:office:office' " +
        "xmlns:w='urn:schemas-microsoft-com:office:word' " +
        "xmlns='http://www.w3.org/TR/REC-html40'>" +
        "<head>" +
        "   <meta name=\"_csrf\" content=\"" + document.querySelector('meta[name="_csrf"]').content + "\"/>" +
        "   <meta name=\"_csrf_header\" content=\"" + document.querySelector('meta[name="_csrf_header"]').content + "\"/>" +
        "   <meta charset='utf-8'>" +
        "   <title>" + document.title + "</title>" +
        "   <style>" + cssText + "</style>" +
        "</head>" +
        "<body>" +
        element.innerHTML +
        printScript +
        "</body>" +
        "</html>";

    var blob = new Blob(['\ufeff', html], {
        type: 'application/msword'
    });

    // Specify link url
    var url = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(html);

    // Specify file name
    filename = filename ? filename + '.doc' : 'document.doc';

    // Create download link element
    var downloadLink = document.createElement("a");

    document.body.appendChild(downloadLink);

    if (navigator.msSaveOrOpenBlob) {
        navigator.msSaveOrOpenBlob(blob, filename);
    } else {
        // Create a link to the file
        downloadLink.href = url;

        // Setting the file name
        downloadLink.download = filename;

        //triggering the function
        downloadLink.click();
    }

    document.body.removeChild(downloadLink);
}

function printElementIncludingStyles(elementId) {
    let element = document.getElementById(elementId);
    if (!element) {
        swal('No element found to print.');
        return;
    }
    let cssText = getAllDomCssRulesAsText();
    let printScript =
        '<script>' +
        'function removeElementsFromDocument(elementId) {' +
        '    let elements = document.getElementsByTagName(elementId);' +
        '    while (elements.length) elements[0].remove();' +
        '}' +
        'removeElementsFromDocument("button");' +
        'removeElementsFromDocument("label");' +
        'removeElementsFromDocument("input");' +
        'window.print();' +
        'window.close();' +
        '</script>';

    var html = "<html>" +
        "<head>" +
        "   <meta name=\"_csrf\" content=\"" + document.querySelector('meta[name="_csrf"]').content + "\"/>" +
        "   <meta name=\"_csrf_header\" content=\"" + document.querySelector('meta[name="_csrf_header"]').content + "\"/>" +
        "   <meta charset='utf-8'>" +
        // "   <title>" + document.title + "</title>" +
        "   <style>" + cssText + "</style>" +
        "</head>" +
        "<body>" +
        element.innerHTML +
        printScript +
        "</body>" +
        "</html>";

    var a = window.open();
    a.document.write(html);

}

function printElementIncludingStylesCustomer(elementId) {
    let element = document.getElementById(elementId);
    if (!element) {
        swal('No element found to print.');
        return;
    }
    let cssText = getAllDomCssRulesAsText();
    let printScript =
        '<script>' +
        'function removeElementsFromDocument(elementId) {' +
        '    let elements = document.getElementsByTagName(elementId);' +
        '    while (elements.length) elements[0].remove();' +
        '}' +
        'removeElementsFromDocument("button");' +
        // 'removeElementsFromDocument("label");' +
        'removeElementsFromDocument("input");' +
        'window.print();' +
        'window.close();' +
        '</script>';

    var html = "<html>" +
        "<head>" +
        "   <meta name=\"_csrf\" content=\"" + document.querySelector('meta[name="_csrf"]').content + "\"/>" +
        "   <meta name=\"_csrf_header\" content=\"" + document.querySelector('meta[name="_csrf_header"]').content + "\"/>" +
        "   <meta charset='utf-8'>" +
        // "   <title>" + document.title + "</title>" +
        "   <style>.col-sm-6{width:450px !important;}" + cssText + "</style>" +
        "<link rel=\"stylesheet\" href=\"/bower_components/bootstrap/dist/css/bootstrap.min.css\">"+
        "</head>" +
        "<body>" +
        element.innerHTML +
        printScript +
        "</body>" +
        "</html>";

    var a = window.open();
    a.document.write(html);

}

function getAllDomCssRulesAsText() {
    let css = [];
    for (let sheeti = 0; sheeti < document.styleSheets.length; sheeti++) {
        let sheet = document.styleSheets[sheeti];
        let rules = ('cssRules' in sheet) ? sheet.cssRules : sheet.rules;
        for (let rulei = 0; rulei < rules.length; rulei++) {
            let rule = rules[rulei];
            if ('cssText' in rule)
                css.push(rule.cssText);
            else
                css.push(rule.selectorText + ' {\n' + rule.style.cssText + '\n}\n');
        }
    }
    return css.join('\n');
}

function removeElementsFromDocument(elementId) {
    let elements = document.getElementsByTagName(elementId);
    while (elements.length) elements[0].remove();
}

function clearObjectPropertiesByFlag(flag, object, ...fields) {
    if (!flag) fields.forEach(field => object[field] = null);
}

function clearSubsequentFields(baseField, object) {
    let baseIndex = indexOfField(baseField, object);
    if (baseIndex >= 0)
        clearFieldValuesStartingFromIndex(++baseIndex, object);// Start clearing from the next field
}

function indexOfField(baseField, object) {
    let props = Object.entries(object);
    let baseIndex;
    for (baseIndex = 0; baseIndex < props.length; baseIndex++) {
        let field = props[baseIndex][0];
        if (field === baseField)
            return baseIndex;
    }
    return -1;
}

function clearFieldValuesStartingFromIndex(baseIndex, object) {
    let props = Object.entries(object);
    for (let i = baseIndex; i < props.length; i++) {

        let fieldName = props[i][0];

        if (typeof object[fieldName] === 'number') {
            object[fieldName] = 0;
        } else if (typeof object[fieldName] === 'boolean') {
            object[fieldName] = false;
        } else if (Array.isArray(object[fieldName])) {
            object[fieldName] = [];
        } else {
            object[fieldName] = null;
        }
    }
}

function checkSingleCharExists(a, b) {
    var res = 0;
    for (var i = 0; i < a.length; i++) {
        if (a[i] == b) {
            res = 1;
            return res;
        }
    }
    return res;
}

var beforeUnloadEvent = function (e) {
    return "Please save changes before leaving the page";
}

function setWindowFocusLossNotification(onBlurMessage) {
    window.onbeforeunload = beforeUnloadEvent;
}

function disableWindowFocusLossNotification() {
    window.onbeforeunload = null;
}

function setObjectPropertyValueFromInputByName(obj, input) {
    if (!obj || !input) return;
    obj[input.name] = input.value;
}

function getSumOfPropertyOfArrayElements(arr, prop) {
    if (!Array.isArray(arr) || !arr.length || !typeof prop === 'string' || !isNumber(arr[0][prop])) return 0;
    return arr.reduce(function (a, b) {
        return a + b[prop];
    }, 0);
}

function numberToBDTFormat(e) {
    e = e == null || e == '0' ? '0.00' : e.toString();
    var realNumber = e.replace(",", "");

    var is_start = 1;
    if (this.checkSingleCharExists(realNumber, ".") == 1) {
        is_start = 0;
    }

    var fl2 = 0;
    var fl = 0;
    var c = 0;
    var n = "";

    for (var i = realNumber.length - 1; i >= 0; i--) {
        if (is_start == 0) {
            if (realNumber[i] == ".") {
                is_start = 1;
            }

            n += realNumber[i];
            continue;
        }

        if (isNaN(realNumber[i])) {
            continue;
        }

        if (fl == 0 && c % 3 == 0 && c > 0) {

            n += ',';
            n += realNumber[i];
            fl = 1;
            c++;
            continue;
        }

        if (fl == 1 && c % 2 != 0 && c > 3) {
            n += ',';
            fl2++;
        }

        c++;
        if (fl2 == 2) {
            fl = 0;
            fl2 = 0;
            c = 1;
        }

        n += realNumber[i];
    }

    var res = n.split("").reverse().join("");
    //var resN=parse(res).toFixed(2);
    return (res);
}


// <script type="text/javascript">
//     document.addEventListener('contextmenu', function(e) {
//         e.preventDefault();
//     });
// document.onkeydown = function(e) {
//     if(event.keyCode == 123) {
//         return false;
//     }
//     if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.keyCode == 'S'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.keyCode == 'H'.charCodeAt(0)){
//         return false;
//     }
//     // if(e.ctrlKey && e.keyCode == 'A'.charCodeAt(0)){
//     //     return false;
//     // }
//     if(e.ctrlKey && e.keyCode == 'F'.charCodeAt(0)){
//         return false;
//     }
//     if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){
//         return false;
//     }
// }





