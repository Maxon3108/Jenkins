import groovy.transform.Field
import org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript


@Field
def repoApp = 'https://<Git repository URL of App>'

@Field
def props = []

@Field
def newParams = []



setNewProps()


// Setup job properties
void setNewProps() {

    //Parameters are unknown at first load
    try {
        regenerateJob = (params.RegenerateJob == null) ? true : params.RegenerateJob
    }
    catch (MissingPropertyException e) {
        regenerateJob = true
    }

    if (regenerateJob) {
        def dynBranchFB = new SecureGroovyScript("""
def brMap = ['sprint':'main','acc':'release','prod':'hotfix']
def brs = brMap[Phase]
String rS = "No \${brs} branch found"
return [rS]
""", true)
        def dynBranch = new SecureGroovyScript("""
def repurl = '${repoApp}'
def brMap = ['sprint':['main'],'acc':['release*'],'prod':['hotfix*']]
String[] brList = []
brMap[Fase].each {
	def proc = "git ls-remote -h \${repurl} \${it}".execute()
	String[] out = proc.text.split('\\n')
	brList += (out - '')
}
if (brList.size() < 1) throw RuntimeException("1")
def res = brList.collect {elem -> elem.split('heads/')[1]}
return res.reverse()
""", false)

        println "Jenkins job ${env.JOB_NAME} gets updated."
        currentBuild.displayName = "#" + Integer.toString(currentBuild.number) + ": Initialize job"

        newParams += [$class: 'ChoiceParameterDefinition', name: 'Phase', choices: ['sprint', 'acc', 'prod']]
        newParams += [$class: 'CascadeChoiceParameter',
                      name: 'Branch',
                      referencedParameters: 'Phase',
                      script: [ $class: 'GroovyScript',
                                script: dynBranch,
                                fallbackScript: dynBranchFB
                      ],
                      choiceType: 'PT_SINGLE_SELECT',
                      description: 'De name of the branch.'
        ]
        newParams += [$class: 'BooleanParameterDefinition', name: 'RegenerateJob', defaultValue: false]

        props += [$class: 'ParametersDefinitionProperty', parameterDefinitions: newParams]
        properties(properties: props)
    }
}
