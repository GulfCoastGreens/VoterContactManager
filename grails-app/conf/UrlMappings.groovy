class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		} // 
                "/contact"(controller:"contact",parseRequest: true){ 
                    action = [GET:"getContactsByType", PUT:"addNewContact", DELETE:"removeContact", POST:"uploadZip"] 
                } 
                "/contact/type"(controller:"contact",parseRequest: true){ 
                    action = [GET:"getContactTypes", PUT:"addNewContactType", DELETE:"removeContactType", POST:"editContactType"] 
                } 
                "/uploadZippedData/uploadZip"(controller:"voterContactManager",parseRequest: true){ 
                    action = [GET:"error", PUT:"error", DELETE:"error", POST:"uploadZip"] 
                } 
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
