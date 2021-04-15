
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
def jobParameters = []
def listServer = []
def playbookListFile = readFile("test.txt").readLines()
playbookListFile.each {
  listServer.add(it)
}
    jobParameters.add(choice(choices: listServer, description: '', name: 'Choice'))
properties([
    parameters(jobParameters)
])
}
