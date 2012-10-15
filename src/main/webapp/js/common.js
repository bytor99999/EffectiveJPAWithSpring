"use strict";

$(document).ready(function(){
    testing.addClicksToNav();
    testing.addClicksInSection("jpa");
    testing.addClicksSpecificInJpaSection();
    testing.addClicksInSection("springData");
});

var testing = {
    showResults: function (data) {
        var resultDiv = document.getElementById('resultDiv');
        resultDiv.innerHTML = JSON.stringify(data, null, 2);
        testing.showInSection("personList", "#resultsTemplates", data)
    },

    showInSection: function (templateName, sectionId, data) {
        var addToContents = ich[templateName](data);
        $(sectionId).html(addToContents);
    },

    getData: function(controllerUrl) {
        $.get(controllerUrl, function(data) {
            testing.showResults(data);
        });
    },

    sendData: function(controllerUrl, formObject) {
        $.post( controllerUrl,
            formObject.serialize(),
            function () {
                testing.showResults("Saved new data");
            }
        ).error(function() {
            showAlert("Add Data Failed");
        });
        formObject.clearForm();
    },

    toggleNav: function(selectedNav) {
        $('#jpa').toggle(false);
        $('#springData').toggle(false);
        $('#'+selectedNav).toggle(true);
    },

    addClicksToNav: function () {
        $("nav").on("click", ".jpa", function(event) {
            event.preventDefault();
            testing.toggleNav("jpa");
        });

        $("nav").on("click", ".springData", function(event) {
            event.preventDefault();
            testing.toggleNav("springData");
        });

        $("#results").on("click", ".clearResults", function(event) {
            event.preventDefault();
            testing.showResults({});
        });

        $("#cleanResults").on("click", ".clearResults", function(event) {
            event.preventDefault();
            testing.showResults({});
        });
    },

    addClicksSpecificInJpaSection: function() {
        var sectionName = "jpa";
        $('#' + sectionName).on("click", '#' + sectionName + 'CascadeButton1', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/cascade/1");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'CascadeButton2', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/cascade/2");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'CacheModeButton1', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/cache/1");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'CacheModeButton2', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/cache/2");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'CacheModeButton3', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/cache/3");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FlushButton1', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/flush/1");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FlushButton2', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/flush/2");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FlushButton3', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/flush/3");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FlushButton4', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/flush/4");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FindButton', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/find");
        });
        $('#' + sectionName).on("click", '#' + sectionName + 'FindAllButtonLazyError', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/lazy/error");
        });
    },

    addClicksInSection: function(sectionName) {
        // Only used for JPA screen
        $('#' + sectionName).on("click", '#' + sectionName + 'FindAllButtonLazy', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/lazy");
        });

        $('#' + sectionName).on("click", '#' + sectionName + 'FindAllButtonEager', function(event) {
            event.preventDefault();
            testing.getData(sectionName + "/people/eager");
        });

        $('#' + sectionName).on("click", '#' + sectionName + 'FindByButton', function(event) {
            event.preventDefault();
            var firstName = $('#' + sectionName + 'SearchFirstName').val();
            testing.getData(sectionName + "/find/" + firstName);
            $('#' + sectionName + 'SearchForm').clearForm();
        });

        $('#' + sectionName).on("click", '#' + sectionName + 'ZipCodeFindByButton', function(event) {
            event.preventDefault();
            var zipCode = $('#' + sectionName + 'SearchZipCode').val();
            testing.getData(sectionName + "/zipcode/" + zipCode);
            $('#' + sectionName + 'ZipSearchForm').clearForm();
        });
    },

    clearAll: function() {
        $(".clearable").empty();
    }
}

$.fn.clearForm = function() {
    return this.each(function() {
        var type = this.type, tag = this.tagName.toLowerCase();
        if (tag === 'form') {
            return $(':input',this).clearForm();
        }
        if (type === 'text' || type === 'password' || tag === 'textarea' || type === "email") {
            this.value = '';
        } else if (type === 'checkbox' || type === 'radio') {
            this.checked = false;
        } else if (tag === 'select') {
            this.selectedIndex = -1;
        }
    });
};