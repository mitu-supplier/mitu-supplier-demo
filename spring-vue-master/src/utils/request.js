import axios from 'axios';



const service = {}
service.install = function fn(Vue) {
    axios.defaults.timeout = 30 * 1000;
    Vue.prototype.$http = axios;

}
export default service;