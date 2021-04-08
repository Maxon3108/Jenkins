// create blank array/list to hold choice options for this parameter and also define any other variables.
def list = []
def file = "test.txt" //var/lib/jenkins/jobs/test

// create a file handle named as textfile 
File textfile= new File(file) 

// now read each line from the file (using the file handle we created above)
textfile.eachLine { line -> 
        //add the entry to a list variable which we'll return at the end. 
        //The following will take care of any values which will have 
        //multiple '-' characters in the VALUE part 
    list.add(line.split('-')[1..-1].join(',').replaceAll(',','-'))
}

//Just fyi - return will work here, print/println will not work inside active choice groovy script / scriptler script for giving mychoice parameter the available options.
return list

println ('Ok')
