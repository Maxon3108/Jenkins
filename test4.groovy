node() {
def jobParameters = []
def playbookListFile = readFile("test.txt").readlines()
playbookListFile.each {
  jobParameters.add(choice(choices: it, description: '', name: 'Choice'))
}
properties([parameters(jobParameters)])
}
pipeline {
    agent any
stages {
  stage('Load groovy'){
    load('test4.groovy')
  }
   stage('Build'){
    steps {
       echo 'Building..'
       echo "$Categories"
       echo "$param.Items"
       echo "$items"
       echo "$populateItems"
      }
    }
   }
}
