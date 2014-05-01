pushd $(dirname ${BASH_SOURCE[0]}) > /dev/null
export BOUNDARY_SDK_HOME=$PWD
popd > /dev/null
export BOUNDARY_SDK_VERSION=$(egrep "<version>(.*)</version>" "$BOUNDARY_SDK_HOME/pom.xml" | head -1 | sed '-e s/<version>//' -e 's/<\/version>//' -e 's/[[:blank:]]//')
export BOUNDARY_MIB_REPOSITORY="$BOUNDARY_SDK_HOME/runtime/mibrepository"
export PATH=$PATH:"$BOUNDARY_SDK_HOME/src/main/scripts"
alias bsdk='cd $BOUNDARY_SDK_HOME'
