/**
 * Created by robinanderson on 1/11/16.
 */

"use strict"

/** Angular module for app */
var app = angular.module( 'app', [ ] )

/**
 * Namespace for auxiliary functions for application.
 * @namespace
 */
app._app = {

    /**
     * "Controller" for Angular - init model and callbacks.
     * @inner
     */
    model_init: function ( $scope, $http ) {
        // === init model(s): ===
        $scope.mod = app._app._get_model_properties()

        // === setup callbacks: ===
        $scope.ctl = app._app._get_controller_functions( $scope.mod, $http )

    },

    /**
     * Initialize and return the data properties used as models on the page.
     * @private
     */
    _get_model_properties: function () {
        return {
            inv_list: [],
            inv_id: null,
            invoice: {},
        }
    },

    /**
     * Initialize and return the data properties used as models on the page.
     * @private
     */
    _get_controller_functions: function ( mod, $http ) {
        return {}  // TODO
    },

}

/** Register "controller" (setup / callback logic) with Angular */
app.controller( 'appCtrl', app._app.model_init )


// vi: ts=4 sw=4 ai expandtab
// *** EOF ***
