node() {
  def fpList = ['Test1':'Тест1', 'AFL':'АФЛ', 'Test3':'Тест4', 'Test4':'Тест4']
  def jobP = []
  fpList.each {
    jobP.add(booleanParam(defaultValue: false, name: it.key, description: it.value))
  }
  properties([
//  disableConcurentBuilds(),
  parameters(jobP)
  ])
  def fpEnable = []
  params.each {

    if(it.getValue() == true) {
      fpEnable.add(it.getKey())

    }
  }
  
}
def metaSend(List fpEnable) {
  
    sh "ls -l"
    fpEnable.each {
      sh "cat meta/${it}/PD.json"
      sh "cat meta/${it}/BS.json"
    }

  
}

pipeline {
    agent any
stages {
    stage('Curl'){
    steps {
      
       metaSend(fpEnable)
       
      }
    }
   }
 }
