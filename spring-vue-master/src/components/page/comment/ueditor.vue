<script>
import baseURL_ from '@/utils/baseUrl.js';
let server_url = baseURL_.baseURL;
// let server_url = "http://192.168.1.103:8088";
export default {
  server_url,
  mounted() {
    // this.loadComponent();
  },
  methods: {
 	loadComponent(editorId) {
      UE.delEditor(editorId);
      var usd = UE.getEditor(editorId);

      var lcoalUrl = window.location.href.split('/#')[0];
      lcoalUrl = lcoalUrl.split('#/')[0];
      UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
      UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'config') {
          return lcoalUrl + '/static/UE/config.json';
        } else if (action == 'uploadimage' || action == 'uploadscrawl') {
          return server_url + '/servbackend/ueditor/upload?Type=image';
        } else if (action == 'listimage') {
          return server_url + '/servbackend/ueditor/listimage';
        } else {
          return this._bkGetActionUrl.call(this, action);
        }
      };
    }
  }
};

</script>
