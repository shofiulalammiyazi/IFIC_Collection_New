
// First Tab Gen-Info
var panelStateGenInfo = 'open',
    openCloseButtonGenInfo = $('.js-open-close-gen-info');

var openClosePanelGenInfo = function() {
    if (panelStateGenInfo === 'closed') {
        panelStateGenInfo = 'open';
        openCloseButtonGenInfo.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStateGenInfo = 'closed';
        openCloseButtonGenInfo.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonGenInfo.click(openClosePanelGenInfo);


// Address
var panelStateAddress = 'closed',
    openCloseButtonAddress = $('.js-open-close-address');

var openClosePanelAddress = function() {
    if (panelStateAddress === 'closed') {
        panelStateAddress = 'open';
        openCloseButtonAddress.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStateAddress = 'closed';
        openCloseButtonAddress.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonAddress.click(openClosePanelAddress);


// Profile
var panelStateProfile = 'closed',
    openCloseButtonProfile = $('.js-open-close-profile');

var openClosePanelProfile = function() {
    if (panelStateProfile === 'closed') {
        panelStateProfile = 'open';
        openCloseButtonProfile.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStateProfile = 'closed';
        openCloseButtonProfile.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonProfile.click(openClosePanelProfile);


// Personal Info
var panelStatePersonalInfo = 'closed',
    openCloseButtonPersonalInfo = $('.js-open-close-personal-info-card');

var openClosePanelPersonalInfo = function() {
    if (panelStatePersonalInfo === 'closed') {
        panelStatePersonalInfo = 'open';
        openCloseButtonPersonalInfo.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStatePersonalInfo = 'closed';
        openCloseButtonPersonalInfo.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonPersonalInfo.click(openClosePanelPersonalInfo);


// account Info
var panelStateAccountInfo = 'closed',
    openCloseButtonAccountInfo = $('.js-open-close-account-info-card');

var openClosePanelAccountInfo = function() {
    if (panelStateAccountInfo === 'closed') {
        panelStateAccountInfo = 'open';
        openCloseButtonAccountInfo.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStateAccountInfo = 'closed';
        openCloseButtonAccountInfo.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonAccountInfo.click(openClosePanelAccountInfo);

// Other  Info
var panelStateOtherInfo = 'closed',
    openCloseButtonOtherInfo = $('.js-open-close-others-info-card');

var openClosePanelOtherInfo = function() {
    if (panelStateOtherInfo === 'closed') {
        panelStateOtherInfo = 'open';
        openCloseButtonOtherInfo.removeClass('fa-plus').addClass('fa-minus');
    } else {
        panelStateOtherInfo = 'closed';
        openCloseButtonOtherInfo.removeClass('fa-minus').addClass('fa-plus');
    }
}

openCloseButtonOtherInfo.click(openClosePanelOtherInfo);