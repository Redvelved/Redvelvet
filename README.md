# Redvelvet
集成开发骨架

# 基于SNMP网络设备监控系统的实现

>* [SNMP简介](#jj)
>* [SNMP网络设备监控系统](#jkxt)
>* [SNMP4j简介](#snmp4j)


<span id="jj"> </span>
##SNMP简介
基本思想：SNMP协议可以为不同厂家，不同类型，不同型号的设备，定义为一个统一的接口和协议，使得管理员可以通过网络，使用统一的规则管理位于不同物理空间的设备，从而大大提高网络管理的效率，简化网络管理员的工作。
![snmp思想](snmpmanager.png)
SNMP 处于OSI七层模型中的应用层协议。在1988年被制定，并被Internet体系结构委员会（IAB）采纳作为一个短期的网络管理解决方案；由于SNMP的简单性，在Internet时代得到了蓬勃的发展，1992年发布了SNMPv2版本，以增强SNMPv1的安全性和功能。现在，已经有了SNMPv3版本。一套完整的SNMP网络设备监控系统系统主要包括管理信息库（MIB）、管理信息结构（SMI）及SNMP报文协议，管理工作站利用SNMP进行远程监控管理网络上的所有支持这种协议的设备(如计算机工作站、终端、路由器、Hub、网络打印机等)，主要负责监视设备状态、修改设备配置、接受事件警告等。

![Nms系统图](nms.png)
###SNMP消息类型
在OSI模型中，传输层的数据单元也称为数据包(packets)。SNMP采用的是UDP(用户数据报协议)作为其传输层协议，并为SNMP提供网络服务，UDP协议的数据单元称为数据报(datagrams)。因为UDP是一种不可靠的数据报服务，所以并不能保证UDP数据报一定能达到目的，但是掉包问题并不对网络管理产生大的影响。SNMP消息包含两个部分：SNMP报头和协议数据单元PDU(Protocol Data Unit)，其中SNMP报头包括SNMP版本号和团体名。SNMP版本号目前有三种：Version1、Version2、Version3。团体标识可以作为SNMP消息的口令，缺省值为"public"。

1. Get_Request：Manager端向Agent端发送读取信息的请求；
2. Get_ Next_Request：Manager端向Agent端发送读取信息的请求；
3. Get_Response：Agent端对Manager端请求的响应；
4. Set_Request：Manager端向Agent端发送设备设置信息，Agent端可根据设置信息来改变设备状态；
5. Trap：当Agent端发生某些事件时，Agent端主动向Manager端发送陷阱信息，如关机事件。

<span id="jkxt"> </span>
##SNMP网络设备监控系统
一个SNMP管理的网络包含三个主要部分：被管理设备、代理和网络管理系统(NMS)。被管理设备就是处于被管理的网络中的多个设备，负责收集和存储管理信息；代理是安装在被管理设备中的软件程序(如大部分交换机自带SNMP代理程序，仅需开启即可)；网络管理系统就是用于监控被管理设备执行状态的软件系统。SNMP进行网络管理时，一般采用Client/Server结构或Manager/Agent结构集中式管理信息的方式，管理工作站为Server端或Manager端，网络中的各个设备为Cient端或Agent端。

###MIB管理消息库
在复杂的网络环境中，网络设备的类型各式各样，所以设备的信息也因设备类型不同而不同，为了将这些信息能通过网络管理系统进行管理，必须采用一套标准来描述这些设备的信息，所以SNMP定义了MIB(Management Information Base)。MIB分为标准MIB和私有MIB，标准MIB适用于所有网络设备，而私有的MIB则由设备厂家向有关机构申请后自行定义。MIB采用树状结构，每个节点每个结点分配了一个字符串和一个小整数作为标号，即OID(Object Identifier)。
![Nms系统图](mib.JPG)
MIB结构树中任一对象的名字就是从根到对象结点的路径上各个节点的标号序列，标号之间用点分隔。如被管理设备中每个网络接口的IP地址信息表示为：iso.org.dod.internet.mgmt.mid.ip，它的数字表示为：1.3.6.1.2.1.4。

标准MIB的基本OID以为1.3.6.1.2.1前缀,而私有的MIB的基本OID以为1.3.6.1.4.1前缀。如果在某一节点下有多个信息，则以列表方式存在，比如IP地址信息中包括子网掩码、网关地址等。

<span id="snmp4j"> </span>
##SNMP4J简介
SNMP4J 是一个企业级的免费开源的SNMP API for Java的类库。基于JAVASE 1.4及以上。官网位于[http://www.snmp4j.org/](http://www.snmp4j.org/)，提供相关JavaDoc和wiki。
![snmp4j结构](snmp4j.png)

### Java Demo（实现对某一网络主机获取主机名称）

```java
/**
 * @Title SNMPTest.java
 * @Description TODO(用一句话描述该文件做什么)
 * @Author yzh yingzh@getui.com
 * @Date 05.30.2016
 */
public class SNMPTest {

    //主机地址
    private String address = "115.236.68.58";
    /**
     * 默认协议
     */
    private String protocol = "udp";
    /**
     * 默认端口
     */
    private int port = 161;
    /**
     * 默认团体名称
     */
    private String community = "compublik";
    /**
     * 默认超时时间
     */
    private long timeout = 5 * 1000L;
    /**
     * 默认重试次数
     */
    private int retry = 3;

    /**
     * 基于SNMPv2协议 为例
     *
     * @throws IOException
     */
    @Test
    public void getRequest() throws IOException {

        //DefaultUdpTransportMapping 作为SNMP 的传输层 协议（UDP）.
        TransportMapping transport = new DefaultUdpTransportMapping();
        //（SNMPv3协议，我们还需要设置安全机制，添加安全用户等等）
        Snmp snmpClient = new Snmp(transport);
        // 开始监听消息
        transport.listen();

        // 生成目标地址对象
        Address address = GenericAddress.parse(this.protocol + ":" + this.address
                + "/" + this.port);
        //创建 target
        Target target = new CommunityTarget(address, new OctetString(this.community));
        //设置版本号
        target.setVersion(SnmpConstants.version2c);
        //设置重试次数
        target.setRetries(this.retry);
        //设置超时时间
        target.setTimeout(this.timeout);

        PDU request = new PDU();
        //设置请求方法方式
        request.setType(PDU.GET);
        //调用的add方法绑定要查询的OID
        request.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.5.0")));
        // 发送报文 并且接受响应
        ResponseEvent respEvent = snmpClient.send(request, target);
        PDU response = respEvent.getResponse();
        if (null!=response){
            if (response.size()>0){
                VariableBinding vb = response.get(0);
                System.out.println("OID: "+vb.getOid().toString());
                System.out.println("Value: "+vb.getVariable().toString());
            }
        }

    }
}

```

**getRequest的console:**
> OID: 1.3.6.1.2.1.1.5.0
>
> Value: com-router
