# Installation

## Erlang
```
$ apt install erlang
```
check version
```
$ erl -eval 'erlang:display(erlang:system_info(otp_release)), halt().'  -noshell
```
## Rabbit-MQ
```
$ apt install rabbitmq-server
```
enable
```
$ systemctl enable rabbitmq-server
$ systemctl start rabbitmq-server
$ systemctl status rabbitmq-server
```
config
```
/etc/rabbitmq/rabbitmq-env.conf
```
## Rabbit-MQ plugins
```
$ rabbitmq-plugins enable rabbitmq_management
Enabling plugins on node rabbit@ryzen-ssd:
rabbitmq_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@ryzen-ssd...
The following plugins have been enabled:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch

started 3 plugins.
```
## Ports
```
$ netstat -plnto | grep '${rabbitmq-pid}'
tcp        0      0 0.0.0.0:25672           0.0.0.0:*               LISTEN      9481/beam.smp        off (0.00/0/0)
tcp        0      0 0.0.0.0:15672           0.0.0.0:*               LISTEN      9481/beam.smp        off (0.00/0/0)
tcp6       0      0 :::5672                 :::*                    LISTEN      9481/beam.smp        off (0.00/0/0)
```
enable on firewall
```
ufw allow from 192.168.1.0/24 to any port 25672 comment "RabbitMQ:25672 for intranet"
ufw allow from 192.168.1.0/24 to any port 15672 comment "RabbitMQ:15672 for intranet"
```



