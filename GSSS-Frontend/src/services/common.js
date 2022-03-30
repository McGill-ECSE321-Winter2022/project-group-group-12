import axios from 'axios';
var config = require('../../config');

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.backendPort;
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

export default axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});