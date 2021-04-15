
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
node() {
def list = ['Server1': ['DB1_1', 'DB1_2'],
            'Server2': ['DB2_1', 'DB2_2', 'DB2_3']
           ]
def jobParameters = []
def listServer = []
def listDB = []
/*def playbookListFile = readFile("test.txt").readLines()
playbookListFile.each {
  listServer.add(it)
}*/
    list.eachWithIndex {
            listServer.add(it)    
    }
    jobParameters.add(choice(choices: listServer, description: '', name: 'Choice'))
properties([
    parameters(jobParameters)
])
}
