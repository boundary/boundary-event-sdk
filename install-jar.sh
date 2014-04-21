#!/bin/bash 

main() {
  typeset -r jar_file_path="$1"
  typeset -r group_id="$2"
  typeset -r artifact_id="$3"
  typeset -r version="$4"
  typeset -r packaging=jar


   if [ $# != 4 ]
   then
     echo "usage: $(basename $0) jar_file_path group_id artifact_id version"
     echo ""
     echo "Example:"
     echo ""
     echo "\$ $(basename $0) $HOME/my-lib-1.2.03.jar org.my my-lib 1.2.03"
     echo ""
     exit 1;
   fi

   mvn install:install-file -Dfile="$jar_file_path" -DgroupId="$group_id" -DartifactId="$artifact_id" -Dversion="$version" -Dpackaging="$packaging"
}

main $*
