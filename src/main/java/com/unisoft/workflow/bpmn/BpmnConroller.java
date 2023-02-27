package com.unisoft.workflow.bpmn;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityListeners;

@Controller
@RequestMapping("/lms/bpmn")
@EntityListeners(AuditingEntityListener.class)
public class BpmnConroller {

//	@Value("${activiti-temp-resource}")
//	private String RESOURCE;

//	@Autowired
//	private XMLWriterDom dom;
//
//	@Autowired
//	private FileIO fileio;
//
//	@Autowired
//	private FlowService flowService;
//
//	@GetMapping(value = "/editor")
//	public String bpmnEditor() {
//		return "bpmn/modeler";
//	}
//
//	@PostMapping(value = "/update")
//	public ResponseEntity<String> bpmnModifier(@RequestBody BpmnData bpmn, BindingResult result)
//			throws IOException, SAXException, XPathExpressionException {
//		if (result.hasErrors()) {
//			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
//		} else {
//			BpmnStatus bs = flowService.getBpmnFile(bpmn.getFileId());
//			dom.prepareXml(bpmn.getXml(), bpmn.getProperties(), RESOURCE + bs.getName());
//			bs.setUpdated(true);
//			bs.setDeployed(false);
//			bs.setVersion(bs.getVersion() + 1);
//			flowService.updateStatus(bs);
//		}
//		return new ResponseEntity<String>("", HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/file/open")
//	public ResponseEntity<String> bpmdFileOpen(@RequestParam(name = "fid", required = true) int fileId) {
//		BpmnStatus bs = flowService.getBpmnFile(fileId);
//		return new ResponseEntity<>(fileio.getBpmnFile(bs.getName()), HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/model/deploy")
//	@ResponseBody
//	public ResponseEntity<HttpStatus> deploy(@RequestParam(name = "fid", required = true) int fileId)
//			throws IOException {
//
//		BpmnStatus bs = flowService.getBpmnFile(fileId);
//		boolean isDeployed = flowService.deployBpmnDiagram(new BpmnDiagram(bs.getName(), RESOURCE));
//		if (isDeployed) {
//			bs.setUpdated(true);
//			bs.setDeployed(true);
//			flowService.updateStatus(bs);
//			return new ResponseEntity<>(HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//
//	@GetMapping(value = "/file/list")
//	@ResponseBody
//	public ResponseEntity<List<BpmnStatus>> getModelList() {
//		List<BpmnStatus> bsList = flowService.getAllBpmnFile();
//		return new ResponseEntity<List<BpmnStatus>>(bsList, HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/status")
//	public String getBpmnStatus(Model model) {
//		model.addAttribute("bpmn", flowService.getBpmnModelStatus());
//		return "status";
//	}
}
