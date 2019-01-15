var diagramUrl = 'http://172.19.241.251:8001/bpmn/start.bpmn';

/**
 * Save diagram contents and print them to the console.
 */
function exportDiagram(bpmnModeler, file_name) {
    bpmnModeler.saveXML({ format: true }, function(err, xml) {
        if (err) {
            return console.error('could not save BPMN 2.0 diagram', err);
        }

        //console.log('DIAGRAM', xml);
        $.ajax({
                url:'saveBPMN',
                data:{
                    'xml': xml,
                    'file_name':file_name
                },
                type:'post',
                success: function (data) {
                    if(data.succeed === true){
                        if(data.object > 0){
                            vm.cpws_finish_num = data.object;
                        }
                        //alert("保存文档" + vm.selectedFile + "成功");
                    }
                    console.log(data);
                }
            }
        )
    });
}

/**
 * Open diagram in our modeler instance.
 *
 * @param bpmnModeler
 * @param {String} bpmnXML diagram to display
 */
function openDiagram(bpmnModeler, bpmnXML, id) {

    // import diagram
    bpmnModeler.importXML(bpmnXML, function(err) {
        if (err) {
            return console.error('could not import BPMN 2.0 diagram', err);
        }
        // access modeler components
        var canvas = bpmnModeler.get("canvas");
        var overlays = bpmnModeler.get('overlays');
        // zoom to fit full viewport
        canvas.zoom('fit-viewport');
        // attach an overlay to a node
        // overlays.add("Process_150x2lw", 'note', {
        //     position: {
        //             bottom: 0,
        //             right: 0
        //     },
        //     html: '<div class="diagram-note">M</div>'
        // });
        // add marker
        // canvas.addMarker("Process_150x2lw", 'needs-discussion');
    });
}
