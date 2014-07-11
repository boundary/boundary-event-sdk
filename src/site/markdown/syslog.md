Syslog Event Adapter Deployment Steps

******************************************************
Pre-reqs
******************************************************
Create CentOS v6 EC2 Instance - ami-eb6b0182
yum update
yum install java-1.6.0-openjdk.x86_64
yum install wget

******************************************************
Download and unpack the latest archive
******************************************************
wget https://github.com/boundary/boundary-event-sdk/releases/download/RE-00.01.02/boundary-event-sdk-00.01.02-dist.tar.gz
tar xvf boundary-event-sdk-00.01.02-dist.tar.gz
rm boundary-event-sdk-00.01.02-dist.tar 
cd boundary-event-sdk-00.01.02

******************************************************
Set environment variables
******************************************************
export BOUNDARY_SDK_HOME=/root/boundary-event-sdk-00.01.02/
export BOUNDARY_ORG_ID=GLr8PfzzPVF1NFrwERRWpIiPDG8
export BOUNDARY_API_KEY=BsFhh7bm84EDan9BCPMvYnJEZGp
export BOUNDARY_MIB_LICENSE="a8 29 19 b4 66 e5 4c 1f / LlSFSvNS"

source etc/boundary-event-sdk
benv

******************************************************
Configure rsyslog
******************************************************
vi /etc/rsyslog.conf

Uncomment
#$ModLoad imudp
#$UDPServerRun 514

Add IP:Port reference to the following to rsyslog.conf. Port should be 1514, this is what the adapter defaults to.

Example:
*.* @10.139.48.31:1514

service rsyslog restart

******************************************************
Start Boundary event adapter daemon
******************************************************
bin/beventd start
