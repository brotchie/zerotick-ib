#!/usr/bin/env python

import zmq
import msgpack

ENDPOINT = 'ipc:///var/tmp/ib'

def main():
    ctx = zmq.Context(1)
    s = ctx.socket(zmq.SUB)
    s.connect(ENDPOINT)
    s.setsockopt(zmq.SUBSCRIBE, "")
    while 1:
        msg = s.recv()
        print msgpack.unpacks(msg)


if __name__ == '__main__':
    main()
