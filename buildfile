# Generated by Buildr 1.4.7, change to your liking


# Version number for this release
VERSION_NUMBER = "1.0.0"
# Group identifier for your projects
GROUP = "zerotick-ib"
COPYRIGHT = "James Brotchie <brotchie@gmail.com>"

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://repo1.maven.org/maven2"
puts repositories.local

desc "The Zerotick-ib project"
define "zerotick-ib" do

  project.version = VERSION_NUMBER
  project.group = GROUP

  compile.with "org.msgpack:msgpack:jar:0.6.6"
  compile.with "com.ib:jtsclient:jar:9.67"
  compile.with "org.zeromq:zmq:jar:1.0.0"

  manifest["Implementation-Vendor"] = COPYRIGHT
  package :jar
end
