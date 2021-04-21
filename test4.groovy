
pipeline {
    agent any
stages {
  
   stage('Build'){
    steps {
       echo 'Building..'
       
      }
    }
   }
 }
def list = ["\"Selecet:selected\"": "\"Not Applicable\"", "\"Server1\"": ["\"DB1_1\"","\"DB1_2\""], "\"Server2\"": ["\"DB2_1\"", "\"DB2_2\"", "\"DB2_3\""]]
def jobParameters = []
def listDB = []    
List listServer = []
    list.each {   
        listServer.add(it.key)
    }
def getServers(List values) {
    
    /*def playbookListFile = readFile("test.txt").readLines()
    playbookListFile.each {
      listServer.add(it)
    }*/
    
    return "return $values"
}


String listS = getServers(listServer)
jobParameters.add([$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT',   name: 'Servers', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: true,
            script:  listS]]])

def getDB(list) {
    
   return htmlBuild() 
}

def htmlBuild(list) {
    html = """
            <html>
            <head>
            <meta charset="windows-1251">
            <style type="text/css">
            div.grayTable {
            text-align: left;
            border-collapse: collapse;
            }
            .divTable.grayTable .divTableCell, .divTable.grayTable .divTableHead {
            padding: 0px 3px;
            }
            .divTable.grayTable .divTableBody .divTableCell {
            font-size: 13px;
            }
            </style>
            </head>
            <body>
        """
        def commitOptions = ""
    getDBlist(Servers, list).each {
        dbOptions += "<option style='font-style: italic' value='DB=${it.getKey()}'>${it}</option>"
    }
    html += """<p style="display: inline-block;">
        <select id="commit_id" size="1" name="value">
            ${dbOptions}
        </select></p></div>"""

    html += """
            </div>
            </div>
            </div>
            </body>
            </html>
         """
    return html
   }
    
    def getDBlist(Servers, list) {
        def listDB = []
        list[Servers].each {
            listDB.add(it)
        }
        return listDB
    }

String listDB = getDB(list)
jobParameters.add([$class: 'CascadeChoiceParameter', choiceType: 'FORMATTED_HTML',name: 'DB', referencedParameters: 'Servers', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return ["error"]'], script: [classpath: [], sandbox: true, 
            script: listDB]]])
/*def getDB(String Servers, list) {
    list[$Servers].each {   
        listDB.add(it)
    }
    return listDB
}
if(binding.hasVariable('$Servers')) {
listDB = getDB($Servers, list)
} else {
   
    listDB = getDB(listServer[0], list)
}
jobParameters.add([$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',name: 'DB', referencedParameters: 'Servers', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return ["error"]'], script: [classpath: [], sandbox: true, 
            script: listDB]]])*/
node() {

properties([
    parameters(jobParameters)
])
}
