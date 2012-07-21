VERSION_NUMBER = "1.0.0"
GROUP = "org.zerotick"
COPYRIGHT = "James Brotchie <brotchie@gmail.com>"

MSGPACK = "org.msgpack:msgpack:jar:0.6.6"
JTSCLIENT = "com.ib:jtsclient:jar:9.67"
ZMQ = "org.zeromq:zmq:jar:1.0.0"

# Default Maven 2.0 repository.
repositories.remote << "http://repo1.maven.org/maven2"
puts repositories.local

desc "The Zerotick-ib project"
define "zerotick-ib" do

  project.version = VERSION_NUMBER
  project.group = GROUP

  compile.with MSGPACK, JTSCLIENT, ZMQ

  manifest["Implementation-Vendor"] = COPYRIGHT

  package :jar

  # jzmq requires the location of libzmq.la to be
  # specified in java.library.path.
  run.using :main => "org.zerotick.ib.client.Server",
            :properties => { "java.library.path" => "/usr/local/lib" }

end
