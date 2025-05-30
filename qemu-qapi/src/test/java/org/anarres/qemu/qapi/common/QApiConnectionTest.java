/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.qemu.qapi.common;

import java.io.File;
import java.net.InetSocketAddress;

import org.anarres.qemu.qapi.api.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shevek
 */
@Disabled // Needs to fire up a test QEmu.
public class QApiConnectionTest {

    private static final Logger LOG = LoggerFactory.getLogger(QApiConnectionTest.class);

    @Test
    public void testConnection() throws Exception {
        QApiConnection connection = new QApiConnection(new InetSocketAddress("localhost", 4444));
        LOG.info("Greeting is " + connection.getGreeting());
        LOG.info("Status is " + connection.call(new QueryStatusCommand()));
        LOG.info("CPUs are " + connection.call(new QueryCpusFastCommand()));
        LOG.info("Chardevs are " + connection.call(new QueryChardevBackendsCommand()));
        LOG.info("Events are " + connection.call(new QueryEventsCommand()));
        LOG.info("DumpMemory is " + connection.call(new QueryDumpGuestMemoryCapabilityCommand()));
        LOG.info("MigrateCapabilities is " + connection.call(new QueryMigrateCapabilitiesCommand()));

        // new MigrateCommand(null);
        if (false) {
            DumpGuestMemoryCommand.Arguments arguments = new DumpGuestMemoryCommand.Arguments();
            arguments.format = DumpGuestMemoryFormat.kdump_snappy;
            arguments.begin = 0L;
            arguments.paging = false;
            arguments.protocol = "foo";
            connection.call(new DumpGuestMemoryCommand(arguments));
        }

        // connection.call(new NbdServerStartCommand(SocketAddress.inet(new InetSocketAddress("localhost", 4445))));
        // connection.call(new NbdServerAddCommand("/dev/vda", false));

        connection.close();
    }


    @Test
    public void testUnixConnection() throws Exception {
        System.out.println("Unix connection test");
        QApiConnection connection = new QApiConnection(AFUNIXSocketAddress.of(new File("/tmp/qemu-monitor-socket")));
        System.out.println("Greeting is " + connection.getGreeting());
        System.out.println("Status is " + connection.call(new QueryStatusCommand()));
        connection.call(new QuitCommand());

        connection.close();
    }
}