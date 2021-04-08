def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0].userId
fruit_fans = '["ann","bob","zoe"]'
def code = sprintf("""
if ( "%s" in %s) {
    choices = ["Apple","Banana","Cherry"]
} else {
    choices = ["Amaranth","Beet","Cabbage"]
}

return choices
""",userId,fruit_fans)

properties([
    parameters([
        [
            $class: 'ChoiceParameter',
            choiceType: 'PT_MULTI_SELECT',
            description: 'Active Choices Reactive parameter',
            filterLength: 1,
            filterable: false,
            name: 'food',
            referencedParameters: 'role',
            script: [
                $class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: false,
                    script: 'return ["error"]'
                ],
                script: [classpath: [],
                    sandbox: false,
                    script: code
                ]
            ]

        ]
    ])
])
node {
    stage("Show Selection"){
        echo "$userId selected $food" 
    }
}
