
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
def list = ['Selecet:selected': 'Not Applicable', 'Server1': ['DB1_1', 'DB1_2'], 'Server2': ['DB2_1', 'DB2_2', 'DB2_3']]
def jobParameters = []
def listDB = []    
def getServers(list) {
    List listServer = []
    list.each {   
        listServer.add(it.key)
    }
    /*def playbookListFile = readFile("test.txt").readLines()
    playbookListFile.each {
      listServer.add(it)
    }*/
    return "return $listServer.it"
}
String listServer = getServers(list)
jobParameters.add([$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT',   name: 'Servers', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: true,
            script:  listServer]]])

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
