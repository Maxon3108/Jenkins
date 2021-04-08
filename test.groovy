node('master') {
def jobParam = []
def listFile = readFile("test.txt").readLines()
listFile.each {
        jobParam.add(choice(choices: listFile, desciption: '', name: 'animals'))
}

properties([disableConcurrentBuilds(), parameters(jobParam)])

println ('Ok')
}
