pushd $(dirname ${BASH_SOURCE[0]})/.. > /dev/null
export BOUNDARY_SDK_HOME=$PWD
popd > /dev/null

# Directory to store javascript files
export BOUNDARY_SDK_SCRIPT_DIR=$BOUNDARY_SDK_HOME/etc/META-INF/js

# API Endpoint Host, use default value if not set
export BOUNDARY_API_HOST=${BOUNDARY_API_HOST:="premium-api.boundary.com"}

# API User e-mail, use current user and hostname if not set
export BOUNDARY_EMAIL=${BOUNDARY_EMAIL:=$USER@$(hostname)}

# API Token Boundary APIs
export BOUNDARY_API_TOKEN=${BOUNDARY_API_TOKEN:=""}

# SNMP4J repository path, use default if not set
export BOUNDARY_MIB_REPOSITORY=${BOUNDARY_MIB_REPOSITORY:="$BOUNDARY_SDK_HOME/etc/mibrepository"}

# SNMP4J license
export BOUNDARY_MIB_LICENSE=${BOUNDARY_MIB_LICENSE:=""}
