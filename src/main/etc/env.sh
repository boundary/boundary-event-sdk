pushd $(dirname ${BASH_SOURCE[0]})/.. > /dev/null
export BOUNDARY_SDK_HOME=$PWD
popd > /dev/null
# API Endpoint Host
export BOUNDARY_API_HOST=premium-api.boundary.com
# API Token for the account
export BOUNDARY_API_TOKEN=
# SNMP4J repository path
export BOUNDARY_MIB_REPOSITORY="$BOUNDARY_SDK_HOME/etc/mibrepository"
# SNMP4J license
export BOUNDARY_MIB_LICENSE=
