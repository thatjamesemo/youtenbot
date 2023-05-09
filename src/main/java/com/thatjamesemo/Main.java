package com.thatjamesemo;

import com.thatjamesemo.commands.SystemToolsBasic;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Hello world! Starting up now");
        Dotenv dotenv = Dotenv.load();

        JDA api = JDABuilder.createDefault(dotenv.get("BOT_TOKEN")).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT).build();
        api.awaitReady();

        api.addEventListener(new SystemToolsBasic());

        for (Guild g : api.getGuilds()) {
            if (g != null) {
                g.updateCommands().addCommands(
                        Commands.slash("ping", "Used to test if the bot is alive")
                ).queue();
            }
        }

        LogFile log = new LogFile("12344", "blahh");


    }
}