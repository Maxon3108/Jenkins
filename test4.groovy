
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
def playbookListFile = readFile("test.txt").readLines()
playbookListFile.each {
  jobParameters.add(choice(choices: it, description: '', name: 'Choice'))
}
properties([
    parameters(jobParameters)
])
}
