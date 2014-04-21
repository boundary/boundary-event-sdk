#!/bin/bash -x

SNMP4J_SMI_ZIP_FILE='snmp4j-smi-1.1.3-dist.zip'
SNMP4J_SMI_URI="http://www.snmp4j.com/smi/$SNMP4J_SMI_ZIP_FILE"

CURL_CMD=curl1
WGET_CMD=wget1
UNZIP_CMD=unzip1

Usage()
{
  echo "usage $(basename $0)"
}

CheckForPreqs()
{
   typeset -i rc=0

   # Check to see if either curl or wget are available
   type "$WGET_CMD"
   if [ $? -ne 0 ]
   then
     type "$CURL_CMD"
     if [ $? -ne 0 ]     
     then
       rc=1
       echo "Could not find either ${WGET_CMD} or ${CURL_CMD} to use. Please install either and run the script again"
   else
    DOWNLOAD_CMD="$WGET_CMD"
   fi

   # Check to see if unzip is available
   type "$UNZIP_CMD"
   if [ $? -ne 0 ]
   then
       rc=1
       echo "Could not find ${UNZIP_CMD}to use. Please install and run the script again"
   fi

   # Check for the install jar script
   if [ ! -x install-jar.sh ]
   then
      rc=1
      echo "Could not find ${UNZIP_CMD}to use. Please install and run the script again"
   fi
   
   return $rc
}

curlDownloadDistribution()
{
  typeset -r file="$1"
  typeset -r url="$2"
  curl "$url" -o "$file"
}

wgetDownloadDistribution()
{
  typeset -r file="$1"
  typeset -r url="$2"
  wget "$url"
}

DownloadDistribution() {
  typeset -r cmd="$1"
  typeset -r file="$2"
  typeset -r url="$3"

  "$cmd"DownloadDistribution "$2" "$3"
}

ExtractDistributions()
{
  typeset file="$1"
  "$UNZIP_COMMAND" "$file"
}

InstallJarsFromDistribution()
{
:
}

Main $*
{

  CheckForPreqs
  if [ $? -eq 1 ]
  then
     exit 1
  fi

}

