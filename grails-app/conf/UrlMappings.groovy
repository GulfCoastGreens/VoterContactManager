class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}
                "/voterContactManager"(controller:"voterContactManager",parseRequest: true){ 
                    action = [GET:"getInit", PUT:"error", DELETE:"error", POST:"error"] 
                } 
                "/search"(controller:"search",parseRequest: true){ 
                    action = [GET:"error", PUT:"error", DELETE:"error", POST:"search"] 
                } 

		"/"(view:"/index",parseRequest: true)
		"500"(view:'/error')
	}
}
