package com.thatjamesemo;

import com.thatjamesemo.commands.SystemToolsBasic;

import com.thatjamesemo.commands.WelcomerCommands;
import com.thatjamesemo.depend.GuildCommandsRegister;
import com.thatjamesemo.depend.LogFile;
import com.thatjamesemo.listeners.MemberJoinListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

/*
.env files and what their values should be:
BOT_TOKEN: The discord token of the bot.
OWNER_ID: The discord ID of the owner (AliceTheFem#5115)

 */

public class Main {

    public static MemberJoinListener memberJoinListener = new MemberJoinListener();

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Hello world! Starting up now");
        Dotenv dotenv = Dotenv.load();

        JDA api = JDABuilder.createDefault(dotenv.get("BOT_TOKEN")).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT).build();
        api.awaitReady();

        api.addEventListener(new SystemToolsBasic());
        api.addEventListener(new MemberJoinListener());
        api.addEventListener(new WelcomerCommands());

        for (Guild g : api.getGuilds()) {
            if (g != null) {
                GuildCommandsRegister.registerCommandsGuild(g);
            }
        }

        LogFile lf = new LogFile(String.valueOf(java.time.LocalDate.now()), "system");
        lf.logItem("Bot Startup!");
    }
}