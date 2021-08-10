package org.featurehouse.mcmod.spm.util.credits;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Experimental
public record ModCredits(List<String> authorGroup,
                         List<ImmutablePair<String, String>> contributors,
                         List<String> collaborators,
                         List<String> importantSupporters) {

    public static ModCredits fromJson(final JsonObject root, Identifier fileId) {
        JsonArray arr;

        arr = JsonHelper.getArray(root, "author_group");
        ArrayList<String> authorGroup = new ArrayList<>();
        for (JsonElement je : arr) {
            if (JsonHelper.isString(je)) authorGroup.add(je.getAsString());
        }

        arr = JsonHelper.getArray(root, "contributors");
        ArrayList<ImmutablePair<String, String>> contributors = new ArrayList<>();
        int count = 0;
        for (JsonElement je : arr) {
            if (je.isJsonObject()) {
                JsonObject o = je.getAsJsonObject();
                String id = JsonHelper.getString(o, "id");
                String name = JsonHelper.getString(o, "name", id);
                contributors.add(ImmutablePair.of(name, id));
            } else if (JsonHelper.isString(je)) {
                String id = je.getAsString();
                contributors.add(ImmutablePair.of(id, id));
            } else throw new JsonSyntaxException("Error parsing " + fileId + '.' +
                    " contributors #" + count + " expected to be a string or an object.");
            count++;
        }

        arr = JsonHelper.getArray(root, "collaborators");
        ArrayList<String> collaborators = new ArrayList<>();
        for (JsonElement je : arr) {
            if (JsonHelper.isString(je)) collaborators.add(je.getAsString());
        }

        arr = JsonHelper.getArray(root, "supporters");
        ArrayList<String> importantSupporters = new ArrayList<>();
        for (JsonElement je : arr) {
            if (JsonHelper.isString(je)) importantSupporters.add(je.getAsString());
        }

        return new ModCredits(authorGroup, contributors, collaborators, importantSupporters);
    }

    public List<String> getAuthorGroup() {
        return authorGroup;
    }

    public List<ImmutablePair<String, String>> getContributors() {
        return contributors;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public List<String> getImportantSupporters() {
        return importantSupporters;
    }
}
