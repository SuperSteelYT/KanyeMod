package org.kanyecraft.kanyemod.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.kanyecraft.kanyemod.rank.Rank;

@CommandParameters(name = "opme", description = "Op yourself.", usage = "/<command>", rank = Rank.NON_OP, source = Source.IN_GAME)
public class Command_opme extends KanyeCommand
{
    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        if (args.length != 0)
        {
            return false;
        }
        action(sender.getName(), "Opping themselves");
        sender.setOp(true);
        sender.sendMessage(opped);
        return true;
    }
}
