/**
 * Created by YuanHao on 2018/7/25.
 *  核心页面
 */
var vm = avalon.define({
    $id: 'dzjz',
    pageIdList: [],
    selectedPage: 1,
    cpws_list : [],
    cpws_dict: {},
    selectedFile:'',
    cpws_rdss: '',
    right_opened_document_list:[],
    right_opened_document_modeler:{},
    cpws_finish_num:0,
    changePage: function (page) {
        vm.selectedPage = page;
        $.ajax({
            url: '/getDocument',
            type: 'get',
            data: {
                start_id: (vm.selectedPage - 1) * 50
            },
            success: function (data) {
                data.forEach(function (item) {
                   vm.cpws_dict[item.file_name] = item.ajjbqk;
                   item['finish'] = '未完成';
                });
                vm.getFinishCpws();
                vm.cpws_list = data;
            }
        })
    },
    init_page_num: function () {
        for(var i = 1; i< 40; i++){
            vm.pageIdList.push(i);
        }
    },
    show_rdss: function (file_name) {
        vm.selectedFile = file_name;
        vm.cpws_rdss = vm.cpws_dict[file_name];
        if(vm.right_opened_document_list.indexOf(file_name) === -1){
            vm.right_opened_document_list.push(file_name);
            vm.get_bpmn_content(file_name);
        }
    },

    get_bpmn_content: function (file_name) {
        $.ajax({
            url: '/getBpmnContent',
            data: {
                file_name:file_name
            },
            type: 'get',
            success: function (data) {
                // modeler instance
                var bpmnModeler = new BpmnJS({
                    container: "#canvas" + vm.selectedFile,
                    keyboard: {
                        bindTo: window
                    }
                });
                vm.right_opened_document_modeler[vm.selectedFile] = bpmnModeler;
                if(data.succeed){
                    openDiagram(bpmnModeler, data.object, vm.selectedFile);
                }else{
                    $.get(diagramUrl, function (result) {
                        openDiagram(bpmnModeler, result, vm.selectedFile);
                    });
                }
            }
        })
    },

    delete_document: function (file_name) {
        var index = vm.right_opened_document_list.indexOf(file_name);
        vm.right_opened_document_list.splice(index, 1);
        if(vm.right_opened_document_list.length > 0){
            vm.selectedFile = vm.right_opened_document_list[0];
        }else{
            vm.selectedFile = '';
        }
        delete vm.right_opened_document_modeler[file_name];
    },

    exportBpmnDiagram: function (file_name) {
        var bpmnModeler = vm.right_opened_document_modeler[file_name];
        exportDiagram(bpmnModeler, file_name);
        vm.getFinishCpws();
    },

    getFinishCpws: function () {
        $.get('/getFinishCpws', function (res) {
            if(res.succeed){
                var resSet = new Set(res.object);
                vm.cpws_list.forEach(function (item) {
                    if(resSet.has(item.file_name)){
                        item.finish = '已完成';
                    }
                });
                vm.cpws_finish_num = res.object.length;
            }
        })
    }
});
vm.$watch("selectedFile", function (new_value, old_value) {
    if(old_value !== ''){
        vm.exportBpmnDiagram(old_value);
    }
});

$(document).ready(function () {
    vm.init_page_num();
    vm.changePage(1);
});




