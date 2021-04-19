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
  //  print params
    if(it.getValue() == true) {
      fpEnable.add(it.getKey())
      print fpEnable
    }
  }
  metaSend(fpEnable)
}
def metaSend(fpEnable) {
  stage('Curl') {
    print fpEnable
  }
}
