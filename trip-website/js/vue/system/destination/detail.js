var vue = new Vue({
    el:"#app",
    data:{
        toasts:[],  //吐司
        dest:{},  //目的地
        catalogs:[], //概况中攻略分类
        strategies:[],  //点击量前3
        page:{}  //游记分页
    },
    methods:{
        commPage:function (page) {
            var param = getParams();
            var p = $("#travelForm").serialize() + "&destId="+param.id + "&currentPage=" + page;
            //游记分页
            ajaxGet("/travels/query?"+p,{}, function (data) {
                vue.page = data.data;
                buildPage(vue.page.number, vue.page.totalPages, vue.doPage)
            })
        },
        doPage:function(page){
            this.commPage(page);
        },
        conditionChange:function(){
            this.commPage(1);
        }
    },
    mounted:function () {
        var param=getParams();
        var _this=this;
        ajaxGet("/destinations/toasts",{destId:param.id},function (data) {
              //  _this.toasts=data.data;
            var list=data.data;
            _this.dest=list.pop();
            _this.toasts=list;
        });
        ajaxGet("/destinations/catalogs",{destId:param.id},function (data) {
            _this.toasts=data.data;
        });

        ajaxGet("/destinations/viewnumTop3",{destId:param.id},function (data) {
            _this.strategies=data.data;
        })
    }


});

