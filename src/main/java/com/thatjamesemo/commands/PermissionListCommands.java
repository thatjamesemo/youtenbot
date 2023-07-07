package com.thatjamesemo.commands;

import com.thatjamesemo.depend.ConfigFile;
import com.thatjamesemo.depend.PermissionChecker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.List;

public class PermissionListCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ConfigFile cf = new ConfigFile(event.getGuild().getIdLong());
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getName().equals("addrolelevel")) {
            event.deferReply().queue();
            eb.clear();

            if (event.getGuild().getOwner() == event.getMember()) {
                int level = event.getOption("level").getAsInt();
                Role role = event.getOption("role").getAsRole();

                List<Object> rolePermsOverall = cf.getOptionAsList("roleperms");
                List<Object> rolePerms = (List<Object>) rolePermsOverall.get(level);

                rolePerms.add(String.valueOf(role.getIdLong()));
                rolePermsOverall.set(level, rolePerms);
                cf.setOption("roleperms", rolePermsOverall);

                StringBuilder sb = new StringBuilder();
                sb.append("Added the role ").append("<@&").append(role.getIdLong()).append("> to the level: ").append(level);

                eb.setTitle("Action Success!").setColor(Color.GREEN);
                eb.setDescription(sb.toString());

                event.getHook().sendMessageEmbeds(eb.build()).queue();
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You are not the owner of the guild, and therefore do not have permissions.");

                event.getHook().sendMessageEmbeds(eb.build()).queue();
            }
        } else if (event.getName().equals("delrolelevel")) {
            event.deferReply().queue();
            eb.clear();

            if (event.getGuild().getOwner() == event.getMember()) {
                int level = event.getOption("level").getAsInt();
                Role role = event.getOption("role").getAsRole();

                List<Object> rolePermsOverall = cf.getOptionAsList("roleperms");
                List<Object> rolePerm = (List<Object>) rolePermsOverall.get(level);

                if (rolePerm.contains(String.valueOf(role.getIdLong()))) {
                    rolePerm.remove(String.valueOf(role.getIdLong()));
                    rolePermsOverall.set(level, rolePerm);
                    cf.setOption("roleperms", rolePermsOverall);

                    StringBuilder sb = new StringBuilder();
                    sb.append("Removed the role ").append("<@&").append(role.getIdLong()).append("> from the level: ").append(level);

                    eb.setTitle("Action Success!").setColor(Color.GREEN);
                    eb.setDescription(sb.toString());


                } else {
                    eb.setTitle("Action Denied").setColor(Color.RED);
                    eb.setDescription("Error: The role is not contained within this level.");
                }
            } else {
                eb.setTitle("Action Denied").setColor(Color.RED);
                eb.setDescription("You are not the owner of the guild, and therefore do not have permissions.");
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        } else if (event.getName().equals("rolelevels")) {
            event.deferReply().queue();
            eb.clear();

            eb.setTitle("Role Levels").setColor(Color.BLUE);
            eb.setDescription("Below is a list of all roles used on that particular level.");
            eb.addField("Role Levels", "Different levels have different permissions. These are the advised roles:\n - Level 0: Owner\n - Level 1: Head Admins\n - Level 2: Admins\n - Level 3: Moderators\n - Level 4: Members\nIt is highly reccomended any members who you want to use commands, should have a member role that is given here. Only allow people with trusted roles with high level permissions, as they mey be allowed to execute actions that are not supported by their role permissions.", false);

            for (int i = 0; i < 5; i++) {
                StringBuilder fieldLevel = new StringBuilder();
                StringBuilder sb = new StringBuilder();
                List<String> rolesAtLevel = PermissionChecker.getPermissionRoles(event.getGuild().getIdLong(), i);

                sb.append("Level ").append(i);
                fieldLevel.append("Roles on this level are: ");

                for (String id : rolesAtLevel) {
                    fieldLevel.append("<@&").append(id).append(">, ");
                }

                eb.addField(sb.toString(), fieldLevel.toString(), true);
            }

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
